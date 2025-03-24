package com.wangsl.common.enums;

/**
 * 设备状态枚举
 */
public enum DeviceStatus {
	UNACTIVATED("unactivated", "未激活"),
	ONLINE("online", "在线"),
	OFFLINE("offline", "离线"),
	DISABLED("disabled", "已禁用");

	private String code;
	private String description;

	DeviceStatus(String code, String description) {
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
