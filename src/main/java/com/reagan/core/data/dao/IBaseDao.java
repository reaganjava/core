package com.reagan.core.data.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

/**
 * <p>Description: 数据库基础DAO类</p>
 * @date 2013年11月12日
 * @author reagan
 * @version 1.0
 * <p>Company:Reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IBaseDao<T> {
	
	/**
	 * 方法用途: 执行sql<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 */
	public void execute(String sql);

	/**
	 * 方法用途: 执行sql<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param args 参数对象
	 */
	public void execute(String sql, Object[] args);
	
	/**
	 * 方法用途: 执行sql<br>
	 * 实现步骤: <br>
	 * @param sql
	 * @param args
	 * @return
	 */
	public int executeReturn(String sql, Object[] args);
	
	/**
	 * 方法用途: 执行sql <br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param args 参数对象
	 * @param types 参数映射类型
	 */
	public void execute(String sql, Object[] args, int[] types);
	
	/**
	 * 方法用途: 批量插入<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param batchArgs 参数对象列表
	 */
	public void executeBatch(String sql, List<Object[]> batchArgs);
	
	/**
	 * 方法用途: 批量插入<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param batchArgs 参数对象列表
	 * @param types 参数映射类型
	 */
	public void executeBatch(String sql, List<Object[]> batchArgs, int[] types);
	
	/**
	 * 方法用途: 批量插入<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param batchArgs 参数对象列表
	 * @param types 参数映射类型
	 */
	public int executeReturn(String sql, Object[] args, int[] types);
	
	/**
	 * 方法用途: 保存记录返回主键<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @return 返回主键
	 */
	public KeyHolder saveRePrimarykey(String sql, Object[] args);
	
	/**
	 * 方法用途: 列表查询<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @param rowMapper 对象结果映射
	 * @return 结果列表
	 */
	public List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper);
	
	
	/**
	 * 方法用途: 列表查询<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @param types 参数映射类型
	 * @param rowMapper 对象结果映射
	 * @return 结果列表
	 */
	public List<T> queryForList(String sql, Object[] args, int[] types, RowMapper<T> rowMapper);

	
	/**
	 * 方法用途: 查询返回单个对象<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @param types 参数映射类型
	 * @param rowMapper 对象结果映射
	 * @return 结果对象
	 */
	public T queryForObject(String sql, Object[] args, int[] types, RowMapper<T> rowMapper);
	
	
	/**
	 * 方法用途: 查询返回单个对象<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @param rowMapper 对象结果映射
	 * @return 结果对象
	 */
	public T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper);
	
	/**
	 * 方法用途: 查询返回单个对象<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @param types 参数映射类型
	 * @param rowMapper 对象结果映射
	 * @return 结果对象
	 */
	public Object queryForValue(String sql, Object[] args, int[] types, Class<?> requiredType);
	
	/**
	 * 方法用途: 查询返回单个对象<br>
	 * 实现步骤: <br>
	 * @param sql SQL语句
	 * @param objs 参数对象
	 * @param rowMapper 对象结果映射
	 * @return 结果对象
	 */
	public Object queryForValue(String sql, Object[] args, Class<?> requiredType);

}
