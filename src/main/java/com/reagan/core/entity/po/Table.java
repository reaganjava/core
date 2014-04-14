package com.reagan.core.entity.po;

import java.util.Date;

import com.reagan.core.annotation.Mapper;

/**
 * <p>Description:表对象 </p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Mapper(tableName="SYS_TABLE")
public class Table {
	
	private Integer id;
	@Mapper(column="TABLENAME")
	private String tableName;
	@Mapper(column="CREATEDATE")
	private Date createDate;
	@Mapper(column="STATUS")
	/**
	 * 状态 0：有效 1：无效 2：已备份
	 */
	private Integer status;
	@Mapper(column="RULE_ID")
	private Integer ruleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	
}
