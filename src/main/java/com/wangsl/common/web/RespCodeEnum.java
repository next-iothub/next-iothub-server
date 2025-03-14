package com.wangsl.common.web;

public enum RespCodeEnum {
	SUCCESS(200, "业务成功"),
	SYSTEM_ERROR(500, "系统异常");

	private int code;
	private String msg;

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

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
