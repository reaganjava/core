package com.reagan.core.data.beans.sys;

import java.util.List;
import java.util.Map;

import com.reagan.core.exception.ConfigException;
import com.reagan.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年11月18日
 * @author rr
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class Process {
	
	/**
	 * 节点计数器
	 */
	private static int currentNode = 1;
	
	private LoggerUtil loggerUtil = new LoggerUtil(Process.class);

	private String className = null;
	
	private Map<String, List<MethodNode>> methods = null;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public Map<String, List<MethodNode>> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, List<MethodNode>> methods) {
		this.methods = methods;
	}

	public MethodNode routeDataSource(String className, String method) {
		loggerUtil.debug("开始路由节点");
		MethodNode methodNode = null;
		//判断是否是该类
		if(this.className.indexOf(className) != -1) {
			for(String key : methods.keySet()) {
				if(method.startsWith(key)) {
					List<MethodNode> methodList = methods.get(key);
					try {
						methodNode = chooseNode(methodList);
						break;
					} catch (ConfigException e) {
						loggerUtil.error(e.getMessage());
					}
				}
			}
		}
		return methodNode;
	}
	
	private MethodNode chooseNode(List<MethodNode> methods) throws ConfigException {
		MethodNode methodNode = null;
		if(methods.size() > 1) {
			//路由大于函数个数时回1
			if(currentNode > methods.size()) {
				currentNode = 1;
			} 
			methodNode = methods.get(currentNode - 1); 
			currentNode++;
		} else {
			methodNode = methods.get(0);
		}
		if(methodNode == null) {
			throw new ConfigException("函数节点配置为空");
		}
		methodNode.setContextHolder();
		return methodNode;
	}

}
