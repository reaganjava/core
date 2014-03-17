package com.reagan.core.data.aop;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import com.reagan.core.data.beans.sys.MethodNode;
import com.reagan.core.data.beans.sys.Process;
import com.reagan.core.exception.MethodNodeException;

import com.reagan.core.util.SysXmlConfig;
import com.reagan.util.LocalCached;
import com.reagan.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年11月15日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class SqlProcessAspect {
	
	private LoggerUtil loggerUtil = new LoggerUtil(SqlProcessAspect.class);

	public void before(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由开始");
	}
	
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		loggerUtil.debug("数据库路由开始");
		String dataSwitch = (String) LocalCached.get("sys.data.config.switch");
		if(dataSwitch.equals("no")) {
			//结果值
			Object result = null;
			Map<String, Process> processes = (Map<String, Process>) SysXmlConfig.getConfigInfo().get(SysXmlConfig.PROCESSES);
			//目标类
			String className = pjp.getTarget().getClass().getName();
			//得到处理对象
			Process process = processes.get(pjp.getTarget().getClass().getName());
			if(process != null) {
				//调用方法
				String methodName = pjp.getSignature().getName();		
				loggerUtil.debug("路由目标对象：" + className + "的方法：" + methodName);
				System.out.println("路由目标对象：" + className + "的方法：" + methodName);
				//得到方法节点
				MethodNode methodNode = process.routeDataSource(className, methodName);
				
				if(methodNode == null) {
					throw new MethodNodeException("节点为空不能执行");
				}
				result = pjp.proceed();
			} else {
				result = pjp.proceed();
			}
			return result;
		} else {
			return pjp.proceed();
		}
		
	}
	
	public void after(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由结束");
	}
}
