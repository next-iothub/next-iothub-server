package com.wangsl.common.enums;

/**
 * 用户状态枚举
 */
public enum UserStatus {
	ACTIVE("active", "激活"),
	INACTIVE("inactive", "未激活"),
	LOCKED("locked", "已锁定");

	private String code;
	private String description;

	UserStatus(String code, String description) {
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
