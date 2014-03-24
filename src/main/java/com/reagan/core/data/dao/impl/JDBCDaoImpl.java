package com.reagan.core.data.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.reagan.core.data.dao.IBaseDao;
import com.reagan.core.data.dao.IJDBCDao;
import com.reagan.core.exception.MapperException;
import com.reagan.core.util.ObjectParams;
import com.reagan.core.util.QueryMapper;
import com.reagan.util.PageBean;


public abstract class JDBCDaoImpl<T> implements IJDBCDao<T> {

	@Autowired
	private IBaseDao<T> baseDao;
	
	@Override
	public void save(T t) {
		ObjectParams<T> objectParams = new ObjectParams<T>();
		try {
			objectParams.objectArrayFactory(t);
			baseDao.execute(objectParams.getSql(), objectParams.getArgs());
		} catch (MapperException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int update(T t) {
		ObjectParams<T> objectParams = new ObjectParams<T>();
		try {
			objectParams.objectArrayUpdateFactory(t);
			return baseDao.executeReturn(objectParams.getSql(), objectParams.getArgs());
		} catch (MapperException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int delete(T t) {		
		ObjectParams<T> objectParams = new ObjectParams<T>();
		try {
			QueryMapper mapper = objectParams.whereMapper(t);
			return baseDao.executeReturn(mapper.toQueryString(), mapper.toQueryArgs());
		} catch (MapperException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public T query(T t, String[] replace) {
		ObjectParams<T> objectParams = new ObjectParams<T>();
		try {
			QueryMapper mapper = objectParams.whereMapper(t);
			return baseDao.queryForObject(mapper.toQueryString(replace), mapper.toQueryArgs(), getRowMapper());
		} catch (MapperException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> queryForList(T t, String[] replace) {
		ObjectParams<T> objectParams = new ObjectParams<T>();
		try {
			QueryMapper mapper = objectParams.whereMapper(t);
			return baseDao.queryForList(mapper.toQueryString(new String[]{"*"}), mapper.toQueryArgs(), getRowMapper());
		} catch (MapperException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PageBean<T> queryForPage(T t, String[] replace, int pageNO, int pageCount) {
		ObjectParams<T> objectParams = new ObjectParams<T>();
		try {
			QueryMapper mapper = objectParams.whereMapper(t);
			PageBean<T> pageBean = new PageBean<T>();
			pageBean.setCurrentPage(pageNO);
			if(pageNO > 0) {
				pageNO = pageNO - 1;
			}
			long count = (long) baseDao.queryForValue(mapper.toQueryString(new String[]{"count(*)"}), mapper.toQueryArgs(), Long.class);
			//设置开始位置
			int startPage = pageNO * pageCount;
			mapper = objectParams.whereMapper(t, startPage, pageCount);
			List<T> resultList = baseDao.queryForList(mapper.toQueryString(replace), mapper.toQueryArgs(), getRowMapper());
			//放入分页容器
			pageBean.setDataList(resultList);
			//设置页大小
			pageBean.setPageSize(pageCount);
			//总记录数
			pageBean.setRecordCount(count);
			return pageBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
