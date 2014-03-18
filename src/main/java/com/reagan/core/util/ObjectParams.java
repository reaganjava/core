package com.reagan.core.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import com.reagan.core.annotation.Mapper;
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
	
	private Object[] args;
	
	private String sql;
	
	public Object[] getArgs() {
		return args;
	}

	public String getSql() {
		return sql;
	}

	public void objectArrayFactory(T t, String sql) {
		List<Object> tempArgs = new ArrayList<Object>();
		StringBuilder sqlBuilder = new StringBuilder(sql + " (");
		StringBuilder paramBuilder = new StringBuilder(" VALUE(");
		for(Field field : t.getClass().getDeclaredFields()) {
			Mapper mapper = field.getAnnotation(Mapper.class);
			if(mapper != null) {
				tempArgs.add(invokeMethod(t, field.getName(), null));
				sqlBuilder.append(mapper.column() + ",");
				paramBuilder.append("?,");
			}
		}
		sqlBuilder.replace(sqlBuilder.length() - 1, sqlBuilder.length(), ")");
		paramBuilder.replace(paramBuilder.length() - 1, paramBuilder.length(), ")");
		sqlBuilder.append(paramBuilder);
		logger.info("执行SQL语句：" + sqlBuilder.toString());
		this.sql = sqlBuilder.toString();
		this.args =  tempArgs.toArray();
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
}
