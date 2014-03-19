package com.reagan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Mapper {
	
	public String tableName() default "";

	public String column() default "";
	
	public boolean insert() default true;
	
	public boolean update() default false;
	
	public boolean updateWhere() default false;
	
}
