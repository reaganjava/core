package com.reagan.core.data.aop;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.reagan.core.data.beans.sys.MethodNode;
import com.reagan.core.data.beans.sys.Process;
import com.reagan.core.data.dao.ICachedDao;
import com.reagan.core.exception.MethodNodeException;

import com.reagan.core.util.SysXmlConfig;
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
	
	@Autowired
	private ICachedDao cachedDao;

	public void before(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由开始");
	}
	
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		loggerUtil.debug("数据库路由开始");
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
			
			methodNode.setCachedDao(cachedDao);
			
			//是否缓存数据
			if(methodNode.isCache()) {
				switch(methodNode.getStyle()) {
					case PK : {
						result = pkCached(pjp, methodNode);
						break;
					}
					case CUSTOM : {
						result = customCached(pjp, methodNode);
						break;
					}
				}
			} else {
				//不需要缓存返回处理结果
				result = pjp.proceed();
			}
		} else {
			result = pjp.proceed();
		}
		return result;
	}
	
	private Object pkCached(ProceedingJoinPoint pjp, MethodNode methodNode) throws Throwable {
		String key = pjp.getArgs()[0].toString();
		Object result = null;
		if(methodNode.isCacheNull(key)) {
			result = pjp.proceed();
			for(Method m : result.getClass().getDeclaredMethods()) {
				if(m.getName().toUpperCase().indexOf("GET" + methodNode.getPrimary().toUpperCase()) != -1) {
					Method method = result.getClass().getDeclaredMethod(m.getName());
					Object pk = method.invoke(result, null);
					if(pk != null) {
						methodNode.writeMethodCache(pk.toString(), result);
					}
				}
			}
		} else {
			//返回缓存
			System.out.println("从缓存读取");
			result = methodNode.readMethodCache(key);
		}
		return result;
	}

	
	private Object customCached(ProceedingJoinPoint pjp, MethodNode methodNode) throws Throwable {
		Object result = null;
		//得到缓存键
		String key = methodNode.getKey();
		//缓存不为空时
		if(methodNode.isCacheNull(key)) {
			//调用方法
			System.out.println("调用方法执行");
			result = pjp.proceed();
			//数据写入缓存
			methodNode.writeMethodCache(key, result);
		} else {
			System.out.println("从缓存读取");
			//读取缓存
			result = methodNode.readMethodCache(key);
		}
		return result;
	}

	
	public void after(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由结束");
	}
}
