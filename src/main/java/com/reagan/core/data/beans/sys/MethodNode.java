package com.reagan.core.data.beans.sys;

import com.reagan.core.util.RoutingContextHolder;

/**
 * <p>Description: 方法节点</p>
 * @date 2013年11月18日
 * @author rr
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MethodNode {
	
	/**
	 * 数据库节点
	 */
	private String dataNode = null;
	
	/**
	 * 处理函数名
	 */
	private String methodName = null;
	
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
}
