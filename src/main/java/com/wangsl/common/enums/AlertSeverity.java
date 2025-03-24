package com.wangsl.common.enums;
/**
 * 告警严重程度枚举
 */
public enum AlertSeverity {
	INFO("info", "信息"),
	WARNING("warning", "警告"),
	ERROR("error", "错误"),
	CRITICAL("critical", "严重");

	private String code;
	private String description;

	AlertSeverity(String code, String description) {
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
