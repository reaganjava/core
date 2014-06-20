package com.reagan.core.util;

public enum JoinMode {
	
	JOIN("JOIN"),
	
	INNER_JOIN("INNER_JOIN"),
	
	LEFT_JOIN("LEFT JOIN"),
	
	RIGHT_JOIN("RIGHT JOIN"),
	
	FULL_JOIN("FULL JOIN");
	
	private JoinMode(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	private String value;
}
