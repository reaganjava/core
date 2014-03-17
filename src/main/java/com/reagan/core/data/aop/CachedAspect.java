package com.reagan.core.data.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.reagan.core.data.beans.sys.MethodNode;
import com.reagan.core.data.dao.ICachedDao;
import com.reagan.util.LoggerUtil;

public class CachedAspect {
	
	@Autowired
	private ICachedDao cachedDao;
	
	private int expired = 3000;
	
	private LoggerUtil loggerUtil = new LoggerUtil(SqlProcessAspect.class);
	
	public void before(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由开始");
	}
	
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}
	
	public void after(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由结束");
	}
	
	private Object pkCached(ProceedingJoinPoint pjp, MethodNode methodNode) throws Throwable {
		String key = pjp.getArgs()[0].toString();
		Object result = null;
		if(isCacheNull(key)) {
			result = pjp.proceed();
			for(Method m : result.getClass().getDeclaredMethods()) {
				if(m.getName().toUpperCase().indexOf("GET" + methodNode.getPrimary().toUpperCase()) != -1) {
					Method method = result.getClass().getDeclaredMethod(m.getName());
					Object pk = method.invoke(result, null);
					if(pk != null) {
						writeMethodCache(pk.toString(), result);
					}
				}
			}
		} else {
			//返回缓存
			System.out.println("从缓存读取");
			result = readMethodCache(key);
		}
		return result;
	}

	
	private Object customCached(ProceedingJoinPoint pjp, MethodNode methodNode) throws Throwable {
		Object result = null;
		//得到缓存键
		String key = methodNode.getKey();
		//缓存不为空时
		if(isCacheNull(key)) {
			//调用方法
			System.out.println("调用方法执行");
			result = pjp.proceed();
			//数据写入缓存
			writeMethodCache(key, result);
		} else {
			System.out.println("从缓存读取");
			//读取缓存
			result = readMethodCache(key);
		}
		return result;
	}
	
	private void writeMethodCache(String key, Object value) throws Exception {
		if(!cachedDao.isMutex(key)) {
			cachedDao.add(key, this.expired, value);
		} else {
			cachedDao.replace(key, this.expired, value);
		}
	}
	
	private void replaceMethodCache(String key, Object value) throws Exception {
		cachedDao.replace(key, this.expired, value);
	}
	
	private Object readMethodCache(String key) throws Exception {
		return cachedDao.get(key);
	}
	
	private boolean isCacheNull(String key) throws Exception {
		System.out.println(cachedDao);
		if(cachedDao.get(key) == null) {
			return true;
		} else {
			return false;
		}
	}

}
