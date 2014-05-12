package com.reagan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @ClassName: Mapper
* @Description: 用于映射数据库表操作的注解对象
* @author reagan
* @date 2014年5月12日 下午2:15:14
*
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Mapper {
	
	/**
	* @Title: tableName
	* @Description: 描述表明
	* @return String
	* @throws
	 */
	public String tableName() default "";

	/**
	* @Title: column
	* @Description: 描述列
	* @return String
	* @throws
	 */
	public String column() default "";
	
	/**
	* @Title: insert
	* @Description: 插入标记为FLASH不生成插入
	* @return boolean
	* @throws
	 */
	public boolean insert() default true;
	
	/**
	* @Title: update
	* @Description: 更新标记为FLASH不生成更新
	* @return boolean
	* @throws
	 */
	public boolean update() default false;
	
	/**
	* @Title: updateWhere
	* @Description: 更新查询条件
	* @return boolean
	* @throws
	 */
	public boolean updateWhere() default false;
	
	/**
	* @Title: like
	* @Description: 模糊查询标记为FLASH时不做模糊查询
	* @return boolean
	* @throws
	 */
	public boolean like() default false;
	
	/**
	* @Title: compare
	* @Description: 查询条件比较关键字默认为= 可以使用 > < >= <=
	* @return String
	* @throws
	 */
	public String compare() default "=";
	
}
