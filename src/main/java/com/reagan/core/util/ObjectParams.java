package com.reagan.core.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>Description: </p>
 * @date 2013年11月27日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ObjectParams<T> {
	
	public Object[] objectArrayFactory(T t) {
		return objectArrayFactory(t, null);
	}
	
	
	public Object[] objectArrayFactory(T t, String[] filterField) {
		Object[] objs = new Object[t.getClass().getDeclaredFields().length];
		int index = 0;
		for(Field field : t.getClass().getDeclaredFields()) {
			if(filterField != null) {
				for(String ffname : filterField) {
					if(ffname.equals(field.getName())) {
						continue;
					} else {
						objs[index] = invokeMethod(t, field.getName(), null);
					}
				}
			} else {
				objs[index] = invokeMethod(t, field.getName(), null);
			}
			index++;
		}
		return objs;
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

		// fieldName -> FieldName
		String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

		Method method = null;
		try {
			method = clazz.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return "";
		}
		try {
			return method.invoke(clazz);
		} catch (Exception e) {
			return "";
		}
	}
}
