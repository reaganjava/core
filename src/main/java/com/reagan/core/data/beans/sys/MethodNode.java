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
	
	private boolean cache = false;
	
	private Style style = null;
	
	private String primary = null;
	
	private String key = null;
	
	private int expired = 0;

	private ICachedDao cachedDao;
	
	public void setCachedDao(ICachedDao cachedDao) {
		this.cachedDao = cachedDao;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}
	
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

	public void writeMethodCache(String key, Object value) throws Exception {
		if(!cachedDao.isMutex(key)) {
			cachedDao.add(key, this.expired, value);
		} else {
			cachedDao.replace(key, this.expired, value);
		}
	}
	
	public void replaceMethodCache(String key, Object value) throws Exception {
		cachedDao.replace(key, this.expired, value);
	}
	
	public Object readMethodCache(String key) throws Exception {
		return cachedDao.get(key);
	}
	
	public boolean isCacheNull(String key) throws Exception {
		System.out.println(cachedDao);
		if(cachedDao.get(key) == null) {
			return true;
		} else {
			return false;
		}
	}
}
