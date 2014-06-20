package com.reagan.core.data.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.KeyHolder;

import com.reagan.core.data.dao.IBaseDao;
import com.reagan.core.data.dao.IMapperDao;
import com.reagan.core.exception.MapperException;
import com.reagan.core.util.ObjectMapperParams;
import com.reagan.core.util.QueryMapper;
import com.reagan.util.PageBean;


public abstract class MapperDaoImpl<T> implements IMapperDao<T> {

	@Autowired
	private IBaseDao<T> baseDao;
	
	private ObjectMapperParams<T> objectMapperParams = new ObjectMapperParams<T>();
	
	@Override
	public IBaseDao<T> getBaseDao() {
		return baseDao;
	}
	
	@Override
	public void save(T t) throws SQLException, DuplicateKeyException, MapperException {
		objectMapperParams.objectArrayFactory(t);
		baseDao.execute(objectMapperParams.getSql(), objectMapperParams.getArgs());
	}
	
	/**
	 * 保存实体对象
	 * @param t 实体对象
	 * @return int 返回主键
	 * @throws MapperException 
	 */
	public Number saveRePk(T t) throws SQLException, MapperException {
		objectMapperParams.objectArrayFactory(t);
		KeyHolder keyHolder = baseDao.saveRePrimarykey(objectMapperParams.getSql(), objectMapperParams.getArgs());
		return keyHolder.getKey();
	}
	
	@Override
	public int update(T t) {
		try {
			objectMapperParams.objectArrayUpdateFactory(t);
			return baseDao.executeReturn(objectMapperParams.getSql(), objectMapperParams.getArgs());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int delete(T t) {		
		try {
			QueryMapper mapper = objectMapperParams.deleteWhereMapper(t);
			return baseDao.executeReturn(mapper.toQueryString(), mapper.toQueryArgs());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public T query(T t)  {
		return query(t, null);
	}

	@Override
	public T query(T t, String[] replaces)  {
		try {
			objectMapperParams.setColumns(replaces);
			QueryMapper mapper = objectMapperParams.queryWhereMapper(t);
			return baseDao.queryForObject(mapper.toQueryString(replaces), mapper.toQueryArgs(), getRowMapper(objectMapperParams));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<T> queryForList(T t) {
		return queryForList(t, null);
	}

	@Override
	public List<T> queryForList(T t, String[] replaces) {
		try {
			objectMapperParams.setColumns(replaces);
			QueryMapper mapper = objectMapperParams.queryWhereMapper(t);
			return baseDao.queryForList(mapper.toQueryString(replaces), mapper.toQueryArgs(), getRowMapper(objectMapperParams));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public PageBean<T> queryForPage(T t, int pageNO, int pageRows) {
		return queryForPage(t, null, pageNO, pageRows);
	}

	@Override
	public PageBean<T> queryForPage(T t, String[] replaces, int pageNO, int pageRows) {
		try {
			objectMapperParams.setColumns(replaces);
			QueryMapper mapper = objectMapperParams.queryWhereMapper(t);
			PageBean<T> pageBean = new PageBean<T>();
			pageBean.setCurrentPage(pageNO);
			if(pageNO > 0) {
				pageNO = pageNO - 1;
			}
			long count = (long) baseDao.queryForValue(mapper.toQueryString(new String[]{"count(*)"}), mapper.toQueryArgs(), Long.class);
			//设置开始位置
			int startPage = pageNO * pageRows;
			mapper = objectMapperParams.whereMapper(t, startPage, pageRows);
			List<T> resultList = baseDao.queryForList(mapper.toQueryString(replaces), mapper.toQueryArgs(), getRowMapper(objectMapperParams));
			//放入分页容器
			pageBean.setDataList(resultList);
			//设置页大小
			pageBean.setPageSize(pageRows);
			//总记录数
			pageBean.setRecordCount(count);
			
			pageBean.setPageCount(count);
			return pageBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
