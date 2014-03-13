package com.reagan.core.data.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reagan.core.data.dao.IPartitionRuleDao;
import com.reagan.core.data.dao.ITableDao;
import com.reagan.core.data.service.IParititonTableService;
import com.reagan.core.entity.bo.ParititonTable;
import com.reagan.core.entity.po.PartitionRule;
import com.reagan.core.entity.po.Table;
import com.reagan.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年12月4日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("parititonTableService")
public class ParititonTableServiceImpl implements IParititonTableService {
	
	private LoggerUtil loggerUtil = new LoggerUtil(ParititonTableServiceImpl.class);
	
	@Autowired
	private IPartitionRuleDao partitionRuleDao;
	
	@Autowired
	private ITableDao tableDao;

	@Transactional
	public void createParition(ParititonTable parititonTable) {
		partitionRuleDao.createUnionTable(parititonTable.getPartitionRule().getUnionCreateSQL());
		partitionRuleDao.savePartitionRule(parititonTable.getPartitionRule());
	}

	
	@Transactional
	public void deletePartition(ParititonTable parititonTable) {
		final List<Table> tables = tableDao.queryTableForList(parititonTable.getTable());
		new Thread() {
			private String dropTableSQL = "DROP TABLE #{tableName}#";
			public void run() {
				for(Table table : tables) {
					try {
						dropTableSQL = dropTableSQL.replace("#{tableName}#", table.getTableName());
						partitionRuleDao.dropTable(dropTableSQL);
					} catch (Exception e) {
						continue;
					}
				}
			}
		};
		tableDao.deleteTable(parititonTable.getTable());
		partitionRuleDao.removePartitionRule(parititonTable.getPartitionRule().getId());
	}

	@Transactional
	public void exectuPartition() {
		List<PartitionRule> partitionRules = partitionRuleDao.queryPartitionRuleForAllList();
		for(PartitionRule partitionRule : partitionRules) {
			//得到最新建表时间
			long startDate = partitionRule.getStartDate().getTime();
			//当前时间
			long currentDate = System.currentTimeMillis();
			loggerUtil.info(startDate + " " + currentDate + " " + (currentDate - startDate));
			//达到建表的间隔时间
			if((currentDate - startDate) > partitionRule.getInterval()) {
				String createSQL = partitionRule.getCreateSQL();
				String unionUpdateSQL = partitionRule.getUnionCreateSQL();
				//用时间作为建表键值
				String tableName = partitionRule.getTableName() + "_" + System.currentTimeMillis();
				loggerUtil.info("====== tableName " + tableName + "======");				
				createSQL = createSQL.replace("#{tableName}#", tableName);
				loggerUtil.info("====== createSQL " + createSQL + "======");
				List<Table> tableList = tableDao.queryTableForAll();
				
				String allTableName = "";
				for(Table t : tableList) {
					allTableName += (t.getTableName() + ",");
				}
				allTableName += tableName;
				unionUpdateSQL = unionUpdateSQL.replace("#{tableName}#", allTableName);
				loggerUtil.info("====== unionUpdateSQL " + unionUpdateSQL + "======");
				partitionRuleDao.execUpdateUnionTable(unionUpdateSQL);
				partitionRuleDao.updateCreateTime(new Date(), partitionRule.getId());
				tableName = "";
			}
		}
	}

}
