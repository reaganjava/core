package com.reagan.core.annotation;

import com.reagan.core.util.JoinMode;

public @interface Join {
	
	public JoinMode mode() default JoinMode.JOIN;
	
	public String primaryKey() default "";
}
