package com.wangsl.common.enums;

/**
 * 设备认证类型枚举
 */
public enum AuthType {
	DEVICE_SECRET("device_secret", "设备密钥"),
	CERT("cert", "证书认证"),
	TOKEN("token", "令牌认证");

	private String code;
	private String description;

	AuthType(String code, String description) {
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
