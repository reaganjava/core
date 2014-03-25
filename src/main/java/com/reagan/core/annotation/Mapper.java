package com.reagan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于数据库与实体的映射
 * @author reagan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Mapper {
	
	/**
	 * 表名映射
	 * @return 数据库表名
	 */
	public String tableName() default "";

	/**
	 * 表字段映射
	 * @return 数据库表字段名
	 */
	public String column() default "";
	
	/**
	 * 是否为插入字段
	 * @return 如果为可以插入字段返回真
	 */
	public boolean insert() default true;
	
	/**
	 * 是否为更新字段
	 * @return 如果为可以更新字段返回真
	 */
	public boolean update() default false;
	
	/**
	 * 是否为更新条件字段
	 * @return 如果为可以更新条件字段返回真
	 */
	public boolean updateWhere() default false;
	
	/**
	 * 模糊查询字段
	 * @return 如果为模糊查询字段返回真
	 */
	public boolean like() default false;
	
	/**
	 * 比较字符串默认为 = ， 可选 >,<,>=,<=
	 * @return 返回查询比较字符串
	 */
	public String compare() default "=";
	
}
