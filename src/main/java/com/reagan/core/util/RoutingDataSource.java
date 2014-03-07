package com.reagan.core.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <p>Description: </p>
 * @date 2013年11月15日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @return   
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return RoutingContextHolder.getDataSourceType();
	}

}
