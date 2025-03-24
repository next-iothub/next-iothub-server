package com.wangsl.common.enums;

/**
 * 事件类型枚举
 */
public enum EventType {
	INFO("info", "信息"),
	ALERT("alert", "告警"),
	ERROR("error", "错误"),
	LIFECYCLE("lifecycle", "生命周期"),
	STATUS("status", "状态变化"),
	CUSTOM("custom", "自定义");

	private String code;
	private String description;

	EventType(String code, String description) {
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
