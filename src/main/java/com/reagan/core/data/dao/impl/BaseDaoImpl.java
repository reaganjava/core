package com.reagan.core.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.reagan.core.data.dao.IBaseDao;
import com.reagan.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年11月15日
 * @author RR
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class BaseDaoImpl<T> extends JdbcDaoSupport implements IBaseDao<T> {
	
	private LoggerUtil logger = new LoggerUtil(BaseDaoImpl.class);
	
	public void execute(String sql) {
		logger.info("execute 函数执行SQL:" + sql);
		this.getJdbcTemplate().execute(sql);
	}
	
	public void execute(String sql, Object[] args) {
		logger.info("execute 函数执行SQL:" + sql + " 参数 :" + args);
		executeReturn(sql, args);
	}
	
	public int executeReturn(String sql, Object[] args) {
		logger.info("executeReturn 函数执行SQL:" + sql + " 参数 :" + args);
		return this.getJdbcTemplate().update(sql, args);
	}
		
	public void execute(String sql, Object[] args, int[] types) {
		logger.info("execute 函数执行SQL:" + sql + " 参数 :" + args + " 类型映射:" + types);
		executeReturn(sql, args, types);
	}
	
	public int executeReturn(String sql, Object[] args, int[] types) {
		logger.info("executeReturn 函数执行SQL:" + sql + " 参数 :" + args + " 类型映射:" + types);
		return this.getJdbcTemplate().update(sql, args, types);
	}
	
	
	public void executeBatch(String sql, List<Object[]> batchArgs) {
		logger.info("executeBatch 函数执行SQL:" + sql + " 参数 :" + batchArgs);
		this.getJdbcTemplate().batchUpdate(sql, batchArgs);
	}
	
	
	public void executeBatch(String sql, List<Object[]> batchArgs, int[] types) {
		logger.info("executeBatch 函数执行SQL:" + sql + " 参数 :" + batchArgs + " 类型映射:" + types);
		this.getJdbcTemplate().batchUpdate(sql, batchArgs, types);
	}
	
	public KeyHolder saveRePrimarykey(final String sql, final Object[] args) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		logger.info("saveRePrimarykey 函数执行SQL:" + sql + " 参数 :" + args);
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for(int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder;
	}

	public List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper) {
		logger.info("queryForList 函数执行SQL:" + sql + " 参数 :" + args + " 返回值映射:" + rowMapper);
		return this.getJdbcTemplate().query(sql, args, rowMapper);
	}
	
	public List<T> queryForList(String sql, Object[] args, int[] types, RowMapper<T> rowMapper) {
		logger.info("queryForList 函数执行SQL:" + sql + " 参数 :" + args + " 类型映射:" + types + " 返回值映射:" + rowMapper);
		return this.getJdbcTemplate().query(sql, args, types, rowMapper);
	}
	
	public T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
		try {
			logger.info("queryForObject 函数执行SQL:" + sql + " 参数 :" + args + " 返回值映射:" + rowMapper);
			return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}
	
	public T queryForObject(String sql, Object[] args, int[] types, RowMapper<T> rowMapper) {
		try {
			logger.info("queryForObject 函数执行SQL:" + sql + " 参数 :" + args + " 类型映射:" + types + " 返回值映射:" + rowMapper);
			return this.getJdbcTemplate().queryForObject(sql, args, types, rowMapper);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}
	
	public T load(String sql, Object pk, RowMapper<T> rowMapper) {
		try {
			logger.info("load 函数执行SQL:" + sql + " 主键值 :" + pk + " 返回值映射:" + rowMapper);
			return this.getJdbcTemplate().queryForObject(sql, new Object[]{pk}, rowMapper);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public Object queryForValue(String sql, Object[] args, int[] types, Class<?> requiredType) {
		try {
			logger.info("queryForValue 函数执行SQL:" + sql + " 参数 :" + args + " 类型映射:" + types + " 返回值映射:" + requiredType);
			return this.getJdbcTemplate().queryForObject(sql, args, types, requiredType);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public Object queryForValue(String sql, Object[] args, Class<?> requiredType) {
		try {
			logger.info("queryForValue 函数执行SQL:" + sql + " 参数 :" + args + "返回值映射:" + requiredType);
			return this.getJdbcTemplate().queryForObject(sql, args, requiredType);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

}
