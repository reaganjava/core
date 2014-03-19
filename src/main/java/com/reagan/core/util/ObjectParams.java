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
	public void objectArrayFactory(T t) {
		List<Object> tempArgs = new ArrayList<Object>();
		Mapper classMapper = t.getClass().getAnnotation(Mapper.class);
		try {
			String tableName = getMapperTable(classMapper);
			StringBuilder sqlBuilder = new StringBuilder("INSERT INTO " + tableName + " (");
			StringBuilder paramBuilder = new StringBuilder(" VALUE(");
			for(Field field : t.getClass().getDeclaredFields()) {
				Mapper mapper = field.getAnnotation(Mapper.class);
				if(mapper != null) {
					if(mapper.insert()) { 
						tempArgs.add(invokeMethod(t, field.getName(), null));
						sqlBuilder.append(mapper.column() + ",");
						paramBuilder.append("?,");
					}
				}
			}
			sqlBuilder.replace(sqlBuilder.length() - 1, sqlBuilder.length(), ")");
			paramBuilder.replace(paramBuilder.length() - 1, paramBuilder.length(), ")");
			sqlBuilder.append(paramBuilder);
			logger.info("执行SQL语句：" + sqlBuilder.toString());
			this.sql = sqlBuilder.toString();
			this.args =  tempArgs.toArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void objectArrayUpdateFactory(T t) {
		List<Object> tempArgs = new ArrayList<Object>();
		Mapper classMapper = t.getClass().getAnnotation(Mapper.class);
		try {
			String tableName = getMapperTable(classMapper);
			StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName + " SET ");
			StringBuilder whereBuilder = new StringBuilder(" WHERE 1=1 ");
			for(Field field : t.getClass().getDeclaredFields()) {
				Mapper mapper = field.getAnnotation(Mapper.class);
				if(mapper != null) {
					if(mapper.update()) { 
						tempArgs.add(invokeMethod(t, field.getName(), null));
						sqlBuilder.append(mapper.column() + "=?,");
					}
					if(mapper.updateWhere()) {
						whereBuilder.append(" AND " + mapper.column() + "=? ");
					}
				}
			}
			sqlBuilder.replace(sqlBuilder.length() - 1, sqlBuilder.length(), " ");
			sqlBuilder.append(whereBuilder);
			logger.info("执行SQL语句：" + sqlBuilder.toString());
			this.sql = sqlBuilder.toString();
			this.args =  tempArgs.toArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public T resultObjectFactory(T t, ResultSet rs) {
		try {
			for(Field field : t.getClass().getDeclaredFields()) {
				Mapper mapper = field.getAnnotation(Mapper.class);
				if(mapper != null) {
					String column = mapper.column();
					System.out.println(column + ":" + field.getName() + ":" + rs.getObject(column));
					BeanUtils.copyProperty(t, field.getName(), rs.getObject(column));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
}
