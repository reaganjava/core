package com.reagan.core.entity.bo;

import com.reagan.core.entity.po.PartitionRule;
import com.reagan.core.entity.po.Table;
import com.reagan.core.enums.TableStatus;

/**
 * <p>Description: </p>
 * @date 2013年12月4日
 * @author RR
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ParititonTable {

	private PartitionRule partitionRule;
	
	private Table table;
	
	private String createStartTime;
	
	private String createEndTime;
	
	/**
	 * 状态 0：有效 1：无效 2：已备份
	 */
	public TableStatus status;
	

	public PartitionRule getPartitionRule() {
		return partitionRule;
	}

	public void setPartitionRule(PartitionRule partitionRule) {
		this.partitionRule = partitionRule;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}
	
}
