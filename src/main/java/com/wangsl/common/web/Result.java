package com.wangsl.common.web;

public class Result<T> {

	private int code;
	private String msg;
	private String status;
	private T data;

	public static <T> Result<T> success() {
		return success(null);
	}

	public static <T> Result<T> fail() {
		return fail(null);
	}

	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<T>();
		result.setCode(RespCodeEnum.SUCCESS.getCode());
		result.setMsg(RespCodeEnum.SUCCESS.getMsg());
		result.setData(data);
		return result;
	}

	public static <T> Result<T> fail(T data) {
		Result<T> result = new Result<T>();
		result.setCode(RespCodeEnum.SYSTEM_ERROR.getCode());
		result.setMsg(RespCodeEnum.SYSTEM_ERROR.getMsg());
		result.setData(data);
		return result;
	}

	public static <T> Result<T> fail(String msg) {
		Result<T> result = new Result<T>();
		result.setCode(RespCodeEnum.SYSTEM_ERROR.getCode());
		result.setMsg(msg);
		return result;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}
}
