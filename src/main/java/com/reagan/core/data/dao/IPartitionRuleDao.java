package com.reagan.core.data.dao;

import java.util.Date;
import java.util.List;


import com.reagan.core.entity.po.PartitionRule;



/**
 * <p>Description:分表策略DAO </p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IPartitionRuleDao {
	
	/**
	 * 方法用途: 创建表<br>
	 * 实现步骤: <br>
	 * @param createSQL 建表SQL
	 */
	public void createTable(String createSQL);
	
	/**
	 * 方法用途: 摧毁表<br>
	 * 实现步骤: <br>
	 * @param createSQL 摧毁表SQL
	 */
	public void dropTable(String dropSQL);
	
	/**
	 * 方法用途: 更新联合表<br>
	 * 实现步骤: <br>
	 * @param unionUpdateSQL 更新联合表SQL
	 */
	public void execUpdateUnionTable(String unionUpdateSQL);
	
	/**
	 * 方法用途: 创建联合表<br>
	 * 实现步骤: <br>
	 * @param createUpdateSQL 创建联合表SQL
	 */
	public void createUnionTable(String createUpdateSQL);
	
	/**
	 * 方法用途: 添加分表策略<br>
	 * 实现步骤: <br>
	 * @param partitionRule 策略对象
	 */
	public void savePartitionRule(PartitionRule partitionRule);
	
	/**
	 * 方法用途: 更新表名<br>
	 * 实现步骤: <br>
	 * @param tableName 表名
	 * @param id 策略ID
	 */
	public void updateTableName(String tableName, int id);
	
	/**
	 * 方法用途: 更新联合表名<br>
	 * 实现步骤: <br>
	 * @param unionTable 联合表名
	 * @param id 策略ID
	 */
	public void updateUnionTable(String unionTable, int id);
	
	/**
	 * 方法用途: 更新建表时间<br>
	 * 实现步骤: <br>
	 * @param interval 建表时间
	 * @param id 策略ID
	 */
	public void updateCreateTime(Date createTime, int id);
	
	/**
	 * 方法用途: 更新间隔时间<br>
	 * 实现步骤: <br>
	 * @param interval 间隔时间
	 * @param id 策略ID
	 */
	public void updateInterval(int interval, int id);
	
	/**
	 * 方法用途: 更新建表，建联合表，更新联合表SQL<br>
	 * 实现步骤: <br>
	 * @param createSQL 建表
	 * @param unionCreateSQL 建联合表
	 * @param unionUpdateSQL 更新联合表
	 * @param id 策略ID
	 */
	public void updateSQL(String createSQL, String unionCreateSQL, String unionUpdateSQL, int id);
	
	/**
	 * 方法用途: 删除策略<br>
	 * 实现步骤: <br>
	 * @param partitionRule 策略对象
	 */
	public void removePartitionRule(int id);
	
	/**
	 * 方法用途: 查询所有建表策略<br>
	 * 实现步骤: <br>
	 * @return 策略列表
	 */
	public List<PartitionRule> queryPartitionRuleForAllList();
	
	/**
	 * 方法用途: 查询建表策略<br>
	 * 实现步骤: <br>
	 * @param partitionRule
	 * @return 策略列表
	 */
	public List<PartitionRule> queryPartitionRuleForList(PartitionRule partitionRule);

}
