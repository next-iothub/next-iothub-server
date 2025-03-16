package com.wangsl.common.web.model;

public enum RespCodeEnum {
	SUCCESS(200, "操作成功"),
	SYSTEM_ERROR(500, "系统错误"),
	UNAUTHORIZED(401, "未认证"),
	FORBIDDEN(403, "无权访问"),
	NOT_FOUND(404, "资源未找到"),
	PARAMS_ERROR(400, "参数错误");

	private final int code;    // 状态码
	private final String msg;  // 状态消息

	RespCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
