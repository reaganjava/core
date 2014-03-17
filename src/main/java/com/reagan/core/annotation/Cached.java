package com.reagan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cached {
	
	public static final int PK = 1;
	
	public static final int CUSTOM = 2;
	
	public int type() default CUSTOM;

	public String key() default "";
	
	public String field() default "";
	
	public int expired() default 3000;

}
