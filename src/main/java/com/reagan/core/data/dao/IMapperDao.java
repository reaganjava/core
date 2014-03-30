package com.reagan.core.data.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.reagan.core.util.ObjectMapperParams;
import com.reagan.util.PageBean;
/**
 * 基础查询类所有的DAO可以直接继承已经实现了基本的数据库操作 
 * @author reagan
 */
public interface IMapperDao<T> {
	
	/**
	 * 保存实体对象
	 * @param t 实体对象
	 */
	public void save(T t) throws Exception;

	/**
	 * 更新实体对象
	 * @param t 实体对象
	 * @return 返回更新记录的行数
	 */
	public int update(T t);

	/**
	 * 删除实体对象
	 * @param t 实体对象
	 * @return 返回删除记录的行数
	 */
	public int delete(T t);
	
	/**
	 * 查询获得实体
	 * @param t 实体对象
	 * @return 返回查询结果实体
	 */
	T query(T t);

	/**
	 * 查询获得实体
	 * @param t 实体对象
	 * @param replace 替换查询字段字符串
	 * @return 返回查询结果实体
	 */
	public T query(T t, String[] replace);
	
	/**
	 * 查询获得列表
	 * @param t 实体对象
	 * @return 返回列表
	 */
	List<T> queryForList(T t);
	
	/**
	 * 查询获得列表
	 * @param t 实体对象
	 * @param replace 替换查询字段字符串
	 * @return 返回列表
	 */
	public List<T> queryForList(T t, String[] replace);
	
	/**
	 * 
	 * @param t 实体对象
	 * @param pageNO 当前页数
	 * @param pageCount 每页记录数
	 * @return 返回分页对象
	 */
	public PageBean<T> queryForPage(T t, int pageNO, int pageCount);

	/**
	 * 
	 * @param t 实体对象
	 * @param replace 替换查询字段字符串
	 * @param pageNO 当前页数
	 * @param pageCount 每页记录数
	 * @return 返回分页对象
	 */
	public PageBean<T> queryForPage(T t, String[] replace, int pageNO, int pageCount);

	/**
	 * 返回实体映射对象
	 * @return 实体映射对象
	 */
	public RowMapper<T> getRowMapper(ObjectMapperParams<T> objectMapperParams);

	IBaseDao<T> getBaseDao();

}
