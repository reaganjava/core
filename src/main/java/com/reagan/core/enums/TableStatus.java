package com.reagan.core.enums;

/**
 * <p>Description: </p>
 * @date 2013年12月4日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum TableStatus {

	effective(0),
	
	invalid(1),
	
	backup(2);
	
	private int value;
	
	private String[] names = {"有效", "无效", "已备份"};
	
	private TableStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return names[value];
	}
}
