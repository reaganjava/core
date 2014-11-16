package com.reagan.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询包装映射类
 * @author reagan
 *
 */
public class QueryMapper {

	/**
	 * 查询语句
	 */
	private StringBuilder queryBuilder = null;
	
	/**
	 * 参数
	 */
	private List<Object> args = null;
	
	public QueryMapper(String query) {
		queryBuilder = new StringBuilder(query);
		args = new ArrayList<Object>();
	}
	
	public StringBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(StringBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public List<Object> getArgs() {
		return args;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}
	
	/**
	 * 返回SQL字符串
	 * @return String
	 */
	public String toQueryString() {
		return queryBuilder.toString();
	}
	
	/**
	 * 返回SQL字符串并可以替换里面的为{0}的占位符
	 * @param replaces 替换的参数
	 * @return String
	 */
	public String toQueryString(String[] replaces) {
		String queryString = queryBuilder.toString();
		if(replaces != null) {
			for(int i = 0; i < replaces.length; i++) {
				queryString = queryString.replace("{" + i + "}", replaces[0]);
			}
		} else {
			queryString = queryString.replace("{" + 0 + "}", "*");
		}
		return queryString;
	}
	
	public Object[] toQueryArgs() {
		return args.toArray();
	}
	
	/**
	 * 添加查询条件与参数
	 * @param where 查询条件
	 * @param arg 参数
	 */
	public void addQueryWhere(String where, Object arg) {
		queryBuilder.append(where);
		if(arg != null) {
			args.add(arg);
		}
	}
	
	public void addQueryString(String query) {
		queryBuilder.append(query);
	}

}
