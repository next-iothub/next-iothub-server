package com.wangsl.common.enums;

/**
 * 产品状态枚举
 */
public enum ProductStatus {
	DEVELOPMENT("development", "开发中"),
	TESTING("testing", "测试中"),
	PUBLISHED("published", "已发布"),
	DEPRECATED("deprecated", "已废弃");

	private String code;
	private String description;

	ProductStatus(String code, String description) {
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
