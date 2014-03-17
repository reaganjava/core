package com.reagan.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;



import com.reagan.core.data.beans.sys.MethodNode;
import com.reagan.core.data.beans.sys.Process;
import com.reagan.core.enums.Style;
import com.reagan.core.exception.ConfigException;
import com.reagan.util.LoggerUtil;
/**
 * <p>Description: 读取配置文件建立PROCESS,METHODNODE</p>
 * @date 2013年11月15日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class DataConfig {

	/**
	 * 日志文件
	 */
	private static LoggerUtil loggerUtil = new LoggerUtil(DataConfig.class);

	/**
	 * 处理器集合
	 */
	private static Map<String, Process> processes = null;

	/**
	 * 方法用途: 用于创建每一个方法执行调用的节点<br>
	 * 实现步骤: 根据XML配置加载每个方法与节点，在每次调用方法时路由节点<br>
	 * @param methodElement XML的节点
	 * @return 节点对象
	 * @throws ConfigException 配置异常
	 */
	private static MethodNode methonNodeFactory(Element methodElement) throws ConfigException {
		MethodNode method = new MethodNode();
		//数据库节点名
		String nodeName = methodElement.attributeValue("node");
		//调用方法名
		String methodName = methodElement.attributeValue("name");
		method.setDataNode(nodeName);
		method.setMethodName(methodName);
		
		return method;
	}
	
	/**
	 * 方法用途: 处理创建工厂<br>
	 * 实现步骤: 每一个处理对应一个CALSS方法，该方法里面定义了访问数据库的类<br>
	 * @param processElement 处理节点
	 * @return 处理对象
	 * @throws ConfigException 配置异常
	 */
	private static Process procesesFactory(Element processElement) throws ConfigException {

		//创建处理对象
		Process process = new Process();
		
		//加载类
		String className = processElement.attributeValue("class");
		process.setClassName(className);
		try {
			//检查类是否有效
			Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new ConfigException("配置的类无效请检查类配置");
		} 
		
		//查找函数节点
		Iterator<?> methodIter = processElement.elementIterator("method");
		Map<String, List<MethodNode>> methodMap = new HashMap<String, List<MethodNode>>();
		//遍历函数节点
		while(methodIter.hasNext()) {
			
			MethodNode methodNode = methonNodeFactory((Element) methodIter.next()); 
			//保存函数节点的LIST
			List<MethodNode> methods = new ArrayList<MethodNode>();
			String methodName = methodNode.getMethodName();
			if(methodMap.get(methodName) != null) {
				//多个相同节点时放入List中
				methods = methodMap.get(methodName);
				methods.add(methodNode);
			} else {
				methods.add(methodNode);
			}
			methodMap.put(methodName, methods);
			process.setMethods(methodMap);
		}
		return process;
	}
	
	/**
	 * 方法用途: 处理结合工厂<br>
	 * 实现步骤: 将多个处理以类名的形式放入到MAP中<br>
	 * @param rootElement XML根节点
	 * @return 返回MAP包含处理对象
	 * @throws ConfigException 配置异常
	 */
	public static Map<String, Process> processesFactory(Element rootElement) throws ConfigException {
		loggerUtil.debug("解析处理开始");
		//存储处理的容器
		processes = new HashMap<String, Process>();
		//遍历处理节点
		Iterator<?> iter = rootElement.elementIterator("processes");
		while(iter.hasNext()) {
			Element processesEle = (Element) iter.next();
			Iterator<?> processIter = processesEle.elementIterator();
			while(processIter.hasNext()) {
				Process process = procesesFactory((Element) processIter.next());
				processes.put(process.getClassName(), process);
			}
		}
		loggerUtil.debug("解析处理结束");
		return processes;
	}
	
}
