package com.wangsl.common.enums;

/**
 * 告警规则操作符枚举
 */
public enum AlertOperator {
	GT("gt", "大于"),
	LT("lt", "小于"),
	GTE("gte", "大于等于"),
	LTE("lte", "小于等于"),
	EQ("eq", "等于"),
	NEQ("neq", "不等于"),
	CHANGE("change", "值变化"),
	IN("in", "在集合中"),
	NOT_IN("not_in", "不在集合中"),
	RANGE("range", "范围内"),
	NOT_RANGE("not_range", "范围外");

	private String code;
	private String description;

	AlertOperator(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
