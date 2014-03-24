package com.reagan.core.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;


import com.reagan.core.annotation.Mapper;
import com.reagan.core.exception.MapperException;
import com.reagan.util.LoggerUtil;
import com.reagan.util.ValidatorUtil;


/**
 * <p>Description: </p>
 * @date 2013年11月27日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ObjectParams<T> {	
	
	private LoggerUtil logger = new LoggerUtil(ObjectParams.class);
	
	/**
	 * 插入参数
	 */
	private Object[] args;
	
	/**
	 * 包装SQL
	 */
	private String sql;
	
	public Object[] getArgs() {
		return args;
	}

	public String getSql() {
		return sql;
	}

	/**
	 * 将对象转换成插入语句
	 * @param t 对象泛型
	 * @param sql 插入语句的前段
	 */
	public void objectArrayFactory(T t) throws MapperException {
		//零时插入参数
		List<Object> tempArgs = new ArrayList<Object>();
		//获取映射注解
		Mapper classMapper = t.getClass().getAnnotation(Mapper.class);
		//返回表明
		String tableName = getMapperTable(classMapper);
		//SQL包装
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO " + tableName + " (");
		StringBuilder paramBuilder = new StringBuilder(" VALUE(");
		for(Field field : t.getClass().getDeclaredFields()) {
			Mapper mapper = field.getAnnotation(Mapper.class);
			//映射不为空时处理
			if(mapper != null) {
				//是否支持插入
				if(mapper.insert()) { 
					Object value = invokeMethod(t, field.getName(), null);
					//值不为空
					if(value != null) {
						tempArgs.add(value);
						sqlBuilder.append(mapper.column() + ",");
						paramBuilder.append("?,");
					}
				}
			}
		}
		//替换『，为）』
		sqlBuilder.replace(sqlBuilder.length() - 1, sqlBuilder.length(), ")");
		paramBuilder.replace(paramBuilder.length() - 1, paramBuilder.length(), ")");
		sqlBuilder.append(paramBuilder);
		logger.info("执行SQL语句：" + sqlBuilder.toString());
		this.sql = sqlBuilder.toString();
		this.args =  tempArgs.toArray();
		
	}
	
	/**
	 * UPDATE操作自动包装
	 * @param t 泛型
	 * @throws MapperException 映射异常
	 */
	public void objectArrayUpdateFactory(T t) throws MapperException {
		List<Object> tempArgs = new ArrayList<Object>();
		List<Object> whereArgs = new ArrayList<Object>();
		Mapper classMapper = t.getClass().getAnnotation(Mapper.class);
		
		String tableName = getMapperTable(classMapper);
		StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName + " SET ");
		StringBuilder whereBuilder = new StringBuilder(" WHERE 1=1 ");
		for(Field field : t.getClass().getDeclaredFields()) {
			Mapper mapper = field.getAnnotation(Mapper.class);
			if(mapper != null) {
				//更新字段
				if(mapper.update()) { 
					Object value = invokeMethod(t, field.getName(), null);
					if(value != null) {
						tempArgs.add(value);
						sqlBuilder.append(mapper.column() + "=?,");
					}
				}
				//更新查询字段
				if(mapper.updateWhere()) {
					Object whereValue = invokeMethod(t, field.getName(), null);
					if(whereValue != null) {
						whereBuilder.append(" AND " + mapper.column() + "=? ");
						whereArgs.add(whereValue);
					} else {
						throw new MapperException("更新查询参数不能为空"); 
					}
				}
			}
		}
		sqlBuilder.replace(sqlBuilder.length() - 1, sqlBuilder.length(), " ");
		sqlBuilder.append(whereBuilder);
		tempArgs.addAll(whereArgs);
		logger.info("执行SQL语句：" + sqlBuilder.toString());
		this.sql = sqlBuilder.toString();
		this.args =  tempArgs.toArray();
	}
	
	/**
	 * 查询结果映射
	 * @param t 泛型
	 * @param rs resultSet数据库结果
	 * @return 返回对象
	 * @throws MapperException 映射异常
	 */
	public T resultObjectFactory(T t, ResultSet rs) throws MapperException {
		try {
			for(Field field : t.getClass().getDeclaredFields()) {
				Mapper mapper = field.getAnnotation(Mapper.class);
				if(mapper != null) {
					String column = mapper.column();
					BeanUtils.copyProperty(t, field.getName(), rs.getObject(column));
				}
			}
		} catch (Exception e) {
			throw new MapperException("返回结果映射对象时出现异常:" + e.getMessage());
		}
		return t;
	}
 
	/**
	 * 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param owner
	 * @param fieldName
	 * @param args
	 * @return
	 */
	private Object invokeMethod(T t, String fieldName, Object[] args) {
		Class<? extends Object> clazz = t.getClass();
		Method method = null;
		// fieldName -> FieldName
		String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);		
		try {
			method = clazz.getMethod("get" + methodName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return "";
		}
		try {
			return method.invoke(t);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获取映射表明
	 * @param classMapper 映射类
	 * @return 返回表明
	 * @throws MapperException 表明为空抛出映射异常
	 */
	private String getMapperTable(Mapper classMapper) throws MapperException {
		if(classMapper != null) {
			String tableName = classMapper.tableName();
			if(ValidatorUtil.isNotEmpty(tableName)) {
				return tableName;
			} else {
				throw new MapperException("表名不能为空必须要有正确的表名");
			}
		} else {
			throw new MapperException("类没有对象映射注解");
		}
	}
	
	public QueryMapper whereMapper(T t) throws MapperException {
		QueryMapper queryMapper = new QueryMapper("SELECT {0} FROM ");
		Class<?> clazz = t.getClass();
		//获取映射注解
		Mapper classMapper = t.getClass().getAnnotation(Mapper.class);
		queryMapper.addQueryString(getMapperTable(classMapper) + " WHERE 1=1 ");
		if(classMapper != null) {
			for(Field field : clazz.getDeclaredFields()) {
				Mapper mapper = field.getAnnotation(Mapper.class);
				if(mapper != null) {
					Object value = invokeMethod(t, field.getName(), null);
					queryMapper = builderWhere(queryMapper, mapper, value);
				}
			}
		} else {
			throw new MapperException("类没有对象映射注解");
		}
		return queryMapper;
	}
	
	public QueryMapper whereMapper(T t, int pageNO, int pageCount) throws MapperException {
		QueryMapper queryMapper = whereMapper(t);
		queryMapper.getQueryBuilder().append(" LIMIT ?, ? ");
		queryMapper.getArgs().add(pageNO);
		queryMapper.getArgs().add(pageCount);
		return queryMapper;
	}
	
	private QueryMapper builderWhere(QueryMapper queryMapper, Mapper mapper, Object value) {
		if(value instanceof String) {
			if(ValidatorUtil.isNotEmpty(value.toString())) {
				sqlWhere(queryMapper, mapper, value);
			}
		} else {
			if(ValidatorUtil.isNotObjectNull(value)) {
				sqlWhere(queryMapper, mapper, value);
			}
		}
		return queryMapper;
	}
	
	private QueryMapper sqlWhere(QueryMapper queryMapper, Mapper mapper, Object value) {
		if(mapper.like()) {
			queryMapper.addQueryWhere(" AND " + mapper.column() + " LIKE ?", "%" + value + "%");
		} else if(mapper.range()) {
			queryMapper.addQueryWhere(" AMD " + mapper.column() + " > ? ", value);
			queryMapper.addQueryWhere(" AMD " + mapper.column() + " < ? ", value);
		} else {
			queryMapper.addQueryWhere(" AND " + mapper.column() + mapper.compare() + "?", value);
		}
		return queryMapper;
	}
	 
}
