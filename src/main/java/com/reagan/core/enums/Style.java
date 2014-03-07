package com.reagan.core.enums;

/**
 * <p>Description: </p>
 * @date 2013年11月22日
 * @author R
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum Style {

	PK(1),
	CUSTOM(2);
	
	private int value;
	
	private Style(int value) {
		this.value = value;
	}
	
	public int getStyleValue() {
		return this.value;
	}
	
	public String toString() {
		return super.toString() + "Style:" + value;
	}
}
