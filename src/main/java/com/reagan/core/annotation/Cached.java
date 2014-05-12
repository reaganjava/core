package com.reagan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @ClassName: Cached
* @Description: 用于在方法上注解缓存
* @author reagan
* @date 2014年5月12日 下午2:08:17
*
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cached {
	
	/**
	 * 表示用主键作为缓存键
	 */
	public static final int PK = 1;
	
	/**
	 * 表示用用户自定义缓存键
	 */
	public static final int CUSTOM = 2;
	
	/**
	* @Title: type
	* @Description: 缓存类型
	* @return int    返回类型
	* @throws
	*/
	public int type() default CUSTOM;

	/**
	* @Title: key
	* @Description: 自定义的键值值
	* @return String    返回类型
	* @throws
	 */
	public String key() default "";
	
	/**
	* @Title: field
	* @Description: 使用主键作为缓存时，设置那个字段为主键
	* @return String    返回类型
	* @throws
	 */
	public String field() default "";
	
	/**
	* @Title: expired
	* @Description: 缓存超时时间
	* @return int
	* @throws
	 */
	public int expired() default 3000;

}
