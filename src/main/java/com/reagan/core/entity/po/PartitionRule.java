package com.reagan.core.entity.po;

import java.util.Date;

import com.reagan.core.annotation.Mapper;

/**
 * <p>Description: 表策略建立</p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Mapper(tableName="SYS_TABLE_RUEL")
public class PartitionRule {

	private Integer id;
	@Mapper(column="TABLENAME")
	private String tableName;
	@Mapper(column="STARTDATE")
	private Date startDate;
	@Mapper(column="INTERVAL")
	private Integer interval;
	@Mapper(column="UNION_TABLE")
	private String unionTable;
	@Mapper(column="CREATESQL")
	private String createSQL;
	@Mapper(column="UNION_CREATESQL")
	private String unionCreateSQL;
	@Mapper(column="UNION_UPDATESQL")
	private String unionUpdateSQL;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getUnionTable() {
		return unionTable;
	}

	public void setUnionTable(String unionTable) {
		this.unionTable = unionTable;
	}

	public String getCreateSQL() {
		return createSQL;
	}

	public void setCreateSQL(String createSQL) {
		this.createSQL = createSQL;
	}

	public String getUnionCreateSQL() {
		return unionCreateSQL;
	}

	public void setUnionCreateSQL(String unionCreateSQL) {
		this.unionCreateSQL = unionCreateSQL;
	}

	public String getUnionUpdateSQL() {
		return unionUpdateSQL;
	}

	public void setUnionUpdateSQL(String unionUpdateSQL) {
		this.unionUpdateSQL = unionUpdateSQL;
	}
	
}
