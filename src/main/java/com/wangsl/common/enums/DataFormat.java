package com.wangsl.common.enums;

/**
 * 数据格式枚举
 */
public enum DataFormat {
	JSON("json", "JSON格式"),
	XML("xml", "XML格式"),
	BINARY("binary", "二进制格式"),
	CUSTOM("custom", "自定义格式");

	private String code;
	private String description;

	DataFormat(String code, String description) {
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
