package com.reagan.core.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.reagan.core.data.dao.IBaseDao;

/**
 * <p>Description: </p>
 * @date 2013年11月15日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class BaseDaoImpl<T> extends JdbcDaoSupport implements IBaseDao<T> {
	
	public void execute(String sql) {
		this.getJdbcTemplate().execute(sql);
	}
	
	public void execute(String sql, Object[] args) {
		executeReturn(sql, args);
	}
	
	public int executeReturn(String sql, Object[] args) {
		return this.getJdbcTemplate().update(sql, args);
	}
		
	public void execute(String sql, Object[] args, int[] types) {
		executeReturn(sql, args, types);
	}
	
	public int executeReturn(String sql, Object[] args, int[] types) {
		return this.getJdbcTemplate().update(sql, args, types);
	}
	
	
	public void executeBatch(String sql, List<Object[]> batchArgs) {
		this.getJdbcTemplate().batchUpdate(sql, batchArgs);
	}
	
	
	public void executeBatch(String sql, List<Object[]> batchArgs, int[] types) {
		this.getJdbcTemplate().batchUpdate(sql, batchArgs, types);
	}
	
	public KeyHolder saveRePrimarykey(final String sql, final Object[] args) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql);
				for(int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder;
	}

	public List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().query(sql, args, rowMapper);
	}
	
	public List<T> queryForList(String sql, Object[] args, int[] types, RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().query(sql, args, types, rowMapper);
	}
	
	public T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
	}
	
	public T queryForObject(String sql, Object[] args, int[] types, RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().queryForObject(sql, args, types, rowMapper);
	}
	
	public T load(String sql, Object pk, RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().queryForObject(sql, new Object[]{pk}, rowMapper);
	}

	@Override
	public Object queryForValue(String sql, Object[] args, int[] types,
			RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().queryForObject(sql, args, types, rowMapper);
	}

	@Override
	public Object queryForValue(String sql, Object[] args,
			RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
	}

}
