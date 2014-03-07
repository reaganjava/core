package com.reagan.core.data.dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.reagan.core.data.dao.IBaseDao;
import com.reagan.core.data.dao.IPartitionRuleDao;
import com.reagan.core.entity.po.PartitionRule;
import com.reagan.core.util.ObjectParams;
import com.reagan.core.util.ValidatorUtil;


/**
 * <p>Description: </p>
 * @date 2013年11月29日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class PartitionRuleDaoImpl implements IPartitionRuleDao {
	
	private static final String INSERT_PARTITION_RULE = "INSERT INTO SYS_TABLE_RULE"
															+ "(TABLENAME,"
															+ "STARTDATE,"
															+ "INTERVALS,"
															+ "UNION_TABLE,"
															+ "CREATE_SQL,"
															+ "UNION_CREATE_SQL,"
															+ "UNION_UPDATE_SQL)"
															+ " VALUES(?,?,?,?,?,?,?)";
	
	private static final String UPDATE_BY_TABLENAME = "UPDATE SYS_TABLE_RULE"
															+ " SET TABLENAME = ?"
															+ " WHERE ID = ?";
	
	private static final String UPDATE_BY_STARTDATE = "UPDATE SYS_TABLE_RULE"
															+ " SET STARTDATE = ?"
															+ " WHERE ID = ?";
	
	private static final String UPDATE_BY_UNION_TABLE = "UPDATE SYS_TABLE_RULE"
															+ " SET UNION_TABLE = ?"
															+ " WHERE ID = ?";
	
	private static final String UPDATE_BY_INTERVALS = "UPDATE SYS_TABLE_RULE"
															+ " SET INTERVALS = ?"
															+ " WHERE ID = ?";
	
	private static final String UPDATE_BY_SQL = "UPDATE SYS_TABLE_RULE"
															+ " SET CREATE_SQL = ?,"
															+ " UNION_CREATE_SQL = ?,"
															+ " UNION_UPDATE_SQL = ?"
															+ " WHERE ID = ?";
															
	
	private static final String DELETE_PARTITION_RULE = "DELETE FROM SYS_TABLE_RULE WHERE ID = ?";
	
	private static final String QUERY_PARTITION_RULE = "SELECT * FROM SYS_TABLE_RULE WHERE 1=1 ";
	
	private static final String WHERE_BY_ID = " AND ID = ?";
	
	private static final String WHERE_BY_TABLE_NAME = " AND LIKE TABLENAME ?";
	
	private static final String WHERE_BY_UNION_TABLE = " AND LIKE UNION_TABLE ?"; 
	
	@Autowired
	private IBaseDao<PartitionRule> baseDao;

	
	
	public void createTable(String createTableSQL) {
		baseDao.execute(createTableSQL);
	}

	
	
	public void dropTable(String dropTableSQL) {
		baseDao.execute(dropTableSQL);
	}


	
	public void execUpdateUnionTable(String unionUpdateSQL) {
		baseDao.execute(unionUpdateSQL);
	}

	
	
	public void createUnionTable(String createUpdateSQL) {
		baseDao.execute(createUpdateSQL);
	}

	
	
	public void savePartitionRule(PartitionRule partitionRule) {
		ObjectParams<PartitionRule> objectParams = new ObjectParams<PartitionRule>();
		Object[] args = objectParams.objectArrayFactory(partitionRule, new String[]{"ID"});
		baseDao.execute(INSERT_PARTITION_RULE, args);
	}

	
	
	public void updateTableName(String tableName, int id) {
		Object[] args = new Object[2];
		args[0] = tableName;
		args[1] = id;
		baseDao.execute(UPDATE_BY_TABLENAME);
	}
	
	
	public void updateUnionTable(String unionTable, int id) {
		Object[] args = new Object[2];
		args[0] = unionTable;
		args[1] = id;
		baseDao.execute(UPDATE_BY_UNION_TABLE);
	}
	
	
	public void updateCreateTime(Date createTime, int id) {
		Object[] args = new Object[2];
		args[0] = createTime;
		args[1] = id;
		baseDao.execute(UPDATE_BY_STARTDATE);
	}	
	
	
	public void updateInterval(int interval, int id) {
		Object[] args = new Object[2];
		args[0] = interval;
		args[1] = id;
		baseDao.execute(UPDATE_BY_INTERVALS);
	}
	
	
	public void updateSQL(String createSQL, String unionCreateSQL, String unionUpdateSQL, int id) {
		Object[] args = new Object[4];
		args[0] = createSQL;
		args[1] = unionCreateSQL;
		args[2] = unionUpdateSQL;
		args[3] = id;
		baseDao.execute(UPDATE_BY_SQL);
	}

	
	public void removePartitionRule(int id) {
		baseDao.execute(DELETE_PARTITION_RULE,new Object[] {id});
	}
	
	
	public List<PartitionRule> queryPartitionRuleForAllList() {
		return queryPartitionRuleForList(null);
	}

	
	public List<PartitionRule> queryPartitionRuleForList(PartitionRule partitionRule) {
		List<Object> args = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(QUERY_PARTITION_RULE);
		if(partitionRule != null) {
			if(partitionRule.getId() != 0) {
				query.append(WHERE_BY_ID);
				args.add(partitionRule.getId());
			}
			if(!ValidatorUtil.isEmpty(partitionRule.getTableName())) {
				query.append(WHERE_BY_TABLE_NAME);
				args.add("%" + partitionRule.getTableName() + "%");
			}
			if(!ValidatorUtil.isEmpty(partitionRule.getUnionTable())) {
				query.append(WHERE_BY_UNION_TABLE);
				args.add("%" + partitionRule.getUnionTable() + "%");
			}
		}
		return baseDao.queryForList(query.toString(), args.toArray(), new PartitionRuleMapper());
	}
	
	class PartitionRuleMapper implements RowMapper<PartitionRule> {

		
		public PartitionRule mapRow(ResultSet rs, int rowNum) throws SQLException {
			PartitionRule partitionRule = new PartitionRule();
			int index = 0;
			try {
				for(Field field : PartitionRule.class.getDeclaredFields()) {
					BeanUtils.setProperty(partitionRule, field.getName(), rs.getObject(index + 1));
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return partitionRule;
		}
		
	}

}
