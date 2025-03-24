package com.wangsl.common.enums;

/**
 * 数据类型枚举
 */
public enum DataType {
	INT("int", "整数"),
	FLOAT("float", "单精度浮点数"),
	DOUBLE("double", "双精度浮点数"),
	TEXT("text", "文本"),
	DATE("date", "日期时间"),
	ENUM("enum", "枚举"),
	BOOL("bool", "布尔值"),
	ARRAY("array", "数组"),
	STRUCT("struct", "结构体"),
	BINARY("binary", "二进制数据");

	private String code;
	private String description;

	DataType(String code, String description) {
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
