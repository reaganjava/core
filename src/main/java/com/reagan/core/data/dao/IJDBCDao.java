package com.reagan.core.data.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.reagan.util.PageBean;

public interface IJDBCDao<T> {

	public void save(T t);

	public int update(T t);

	public int delete(T t);

	public T query(T t, String[] replace);

	public List<T> queryForList(T t, String[] replace);

	public PageBean<T> queryForPage(T t, String[] replace, int pageNO, int pageCount);

	public RowMapper<T> getRowMapper();
}
