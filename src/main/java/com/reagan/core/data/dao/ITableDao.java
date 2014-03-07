package com.reagan.core.data.dao;

import java.util.List;

import com.reagan.core.entity.po.Table;

/**
 * <p>Description: </p>
 * @date 2013年11月29日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface ITableDao {

	public void saveTable(Table table);
	
	public void deleteTable(Table table);
	
	public List<Table> queryTableForAll();
	
	public List<Table> queryTableForList(Table table);
	
	public List<Table> queryTableForList(Table table, String createStartTime, String endStartTime);
	
}
