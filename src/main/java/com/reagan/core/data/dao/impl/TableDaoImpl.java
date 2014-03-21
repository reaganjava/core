package com.reagan.core.data.dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.reagan.core.data.dao.IBaseDao;
import com.reagan.core.data.dao.ITableDao;
import com.reagan.core.entity.po.Table;
import com.reagan.core.exception.MapperException;
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
@Repository
public class TableDaoImpl implements ITableDao {
	
	private static final String DELETE_TABLE = "DELETE FROM SYS_TABLE WHERE 1=1";

	private static final String DELETE_WHERE_BY_ID = " AND ID = ?";
	
	private static final String DELETE_WHERE_BY_RULE_ID = " AND RULE_ID = ?";
	
	private static final String QUERY_TABLE = "SELECT * FROM SYS_TABLE WHERE 1=1 ";
	
	private static final String WHERE_TABLE_NAME_LIKE = " AND LIKE TABLENAME ?";
	
	private static final String WHERE_TABLE_RULE_ID = " AND RULE_ID = ?";
	
	private static final String WHERE_TABLE_CREATEDATE = " AND CREATEDATE >= ? AND CREATEDATE <= ?";
	
	@Autowired
	private IBaseDao<Table> baseDao;

	
	
	public void saveTable(Table table) {
		ObjectParams<Table> objectParams = new ObjectParams<Table>();
		try {
			objectParams.objectArrayFactory(table);
			baseDao.execute(objectParams.getSql(), objectParams.getArgs());
		} catch (MapperException e) {
			e.printStackTrace();
		}
	}

	
	public void deleteTable(Table table) {
		List<Object> args = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(DELETE_TABLE);
		if(table.getId() != 0) {
			args.add(table.getId());
			query.append(DELETE_WHERE_BY_ID);
		}
		if(table.getRuleId() != 0) {
			args.add(table.getRuleId());
			query.append(DELETE_WHERE_BY_RULE_ID);
		}
		baseDao.execute(query.toString(), args.toArray());
	}
	
	
	public List<Table> queryTableForAll() {
		return queryTableForList(null);
	}
	
	
	public List<Table> queryTableForList(Table table) {
		return queryTableForList(table, null, null);
	}

	
	
	public List<Table> queryTableForList(Table table, String createStartTime, String createEndTime) {
		List<Object> args = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(QUERY_TABLE);
		if(table != null) {
			if(table.getRuleId() != 0) {
				query.append(WHERE_TABLE_RULE_ID);
				args.add(table.getRuleId());
			}
			if(!ValidatorUtil.isEmpty(table.getTableName())) {
				query.append(WHERE_TABLE_NAME_LIKE);
				args.add("%" + table.getTableName() + "%");
			}
			if(!ValidatorUtil.isEmpty(createStartTime) && !ValidatorUtil.isEmpty(createEndTime)) {
				query.append(WHERE_TABLE_CREATEDATE);
				args.add(createStartTime);
				args.add(createEndTime);
			}
		}
		return baseDao.queryForList(query.toString(), args.toArray(), new TableMapper());
	}
	
	class TableMapper implements RowMapper<Table> {

		
		
		public Table mapRow(ResultSet rs, int rowNum) throws SQLException {
			Table table = new Table();
			int index = 0;
			try {
				for(Field field : Table.class.getDeclaredFields()) {
					BeanUtils.setProperty(table, field.getName(), rs.getObject(index + 1));
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return table;
		}
		
	}

}
