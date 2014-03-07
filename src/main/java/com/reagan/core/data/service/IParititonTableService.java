package com.reagan.core.data.service;

import com.reagan.core.entity.bo.ParititonTable;

/**
 * <p>Description: </p>
 * @date 2013年12月4日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IParititonTableService {

	public void createParition(ParititonTable parititonTable);
	
	public void deletePartition(ParititonTable parititonTable);
	
	public void exectuPartition();
}
