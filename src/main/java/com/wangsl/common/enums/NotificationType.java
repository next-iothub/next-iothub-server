package com.wangsl.common.enums;

/**
 * 通知类型枚举
 */
public enum NotificationType {
	EMAIL("email", "电子邮件"),
	SMS("sms", "短信"),
	PUSH("push", "应用推送"),
	WEBHOOK("webhook", "Web钩子"),
	IN_APP("in_app", "应用内消息");

	private String code;
	private String description;

	NotificationType(String code, String description) {
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
