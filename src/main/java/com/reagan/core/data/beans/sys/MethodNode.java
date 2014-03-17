package com.reagan.core.data.beans.sys;


import com.reagan.core.data.dao.ICachedDao;
import com.reagan.core.enums.Style;
import com.reagan.core.util.RoutingContextHolder;

/**
 * <p>Description: </p>
 * @date 2013年11月18日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MethodNode {
	
	private String dataNode = null;
	
	private String methodName = null;
	
	private Style style = null;
	
	private String primary = null;
	
	private String key = null;
	
	private int expired = 0;
	
	public String getDataNode() {
		return dataNode;
	}

	public void setDataNode(String dataNode) {
		this.dataNode = dataNode;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public void setContextHolder() {
		RoutingContextHolder.setDataSourceType(this.dataNode);
	}
	
	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}
}
