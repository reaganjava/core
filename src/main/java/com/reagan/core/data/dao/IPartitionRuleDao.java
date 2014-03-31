package com.reagan.core.data.dao;

import com.reagan.core.entity.po.PartitionRule;




/**
 * <p>Description:分表策略DAO </p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IPartitionRuleDao extends IMapperDao<PartitionRule> {
	
	public void createTableRule(String createSQL);
	
	public void updateTableRule(String updateSQL);

}
