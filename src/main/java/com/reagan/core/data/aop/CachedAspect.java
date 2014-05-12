package com.reagan.core.data.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.reagan.core.annotation.Cached;
import com.reagan.core.data.dao.ICachedDao;
import com.reagan.util.LoggerUtil;
/**
 * <p>Description: 缓存拦截器用于在缓存中存储数据</p>
 * @date 2014年5月12日 下午2:31:30
 * @author reagan
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2014</p>
 */
public class CachedAspect {
	
	/**
	 * 缓存DAO
	 */
	@Autowired
	private ICachedDao cachedDao;
	
	/**
	 * 默认超时时间
	 */
	private int expired = 3000;
	
	private LoggerUtil loggerUtil = new LoggerUtil(SqlProcessAspect.class);
	
	public void before(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由开始");
	}
	
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		//得到对象类
		Class<?> clazz = pjp.getTarget().getClass();
		//得到方法名字
		String methodName = pjp.getSignature().getName();
		Method[] methods = clazz.getMethods();
		Cached cached = null;
		Object result = null;
		for(Method method : methods) {
			if(method.getName().equals(methodName)) {
				//注解对象
				cached = method.getAnnotation(Cached.class);
				break;
			}
		}
		if(cached != null) {
			//缓存类型
			if(cached.type() == 1) {
				String fieldName = cached.field();
				result = pkCached(pjp, fieldName);
			} else if(cached.type() == 2) {
				String key = cached.key();
				result = customCached(pjp, key);
			}
		}
		if(result != null) {
			return result;
		} 
		return pjp.proceed();
	}
	
	public void after(JoinPoint joinPoint) {
		loggerUtil.debug("数据库路由结束");
	}
	
	/**
	* 方法用途: 采用主键作为缓存的情况调用<br>
	* 实现步骤: <br>
	* @param pjp切入点对象，
	* @param fieldName 字段对象
	* @return 返回Object
	* @throws Throwable
	 */
	private Object pkCached(ProceedingJoinPoint pjp, String fieldName) throws Throwable {
		String key = pjp.getArgs()[0].toString();
		Object result = null;
		if(isCacheNull(key)) {
			result = pjp.proceed();
			for(Method m : result.getClass().getDeclaredMethods()) {
				if(m.getName().toUpperCase().indexOf(fieldName.toUpperCase()) != -1) {
					Method method = result.getClass().getDeclaredMethod(m.getName());
					Object pk = method.invoke(result, null);
					if(pk != null) {
						//写入缓存
						writeMethodCache(pk.toString(), result);
					}
				}
			}
		} else {
			//返回缓存
			loggerUtil.info("从缓存读取");
			result = readMethodCache(key);
		}
		return result;
	}

	
	private Object customCached(ProceedingJoinPoint pjp, String key) throws Throwable {
		Object result = null;
		//缓存不为空时
		if(isCacheNull(key)) {
			//调用方法
			loggerUtil.info("调用方法执行");
			result = pjp.proceed();
			//数据写入缓存
			writeMethodCache(key, result);
		} else {
			loggerUtil.info("从缓存读取");
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
