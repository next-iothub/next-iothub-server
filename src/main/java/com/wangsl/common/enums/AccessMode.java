package com.wangsl.common.enums;

/**
 * 属性访问模式枚举
 */
public enum AccessMode {
	READ_ONLY("read_only", "只读"),
	READ_WRITE("read_write", "读写"),
	WRITE_ONLY("write_only", "只写");

	private String code;
	private String description;

	AccessMode(String code, String description) {
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
