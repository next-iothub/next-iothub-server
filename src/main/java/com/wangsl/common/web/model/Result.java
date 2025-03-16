package com.wangsl.common.web.model;

import java.lang.reflect.Field;

public class Result<T> {

	private int code;
	private String msg;
	private T data;

	public static <T> Result<T> success() {
		return success(null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<T>()
			.code(RespCodeEnum.SUCCESS.getCode())
			.msg(RespCodeEnum.SUCCESS.getMsg())
			.data(data);
	}

	public static <T> Result<T> fail() {
		return fail(RespCodeEnum.SYSTEM_ERROR.getCode(), RespCodeEnum.SYSTEM_ERROR.getMsg());
	}

	public static <T> Result<T> fail(String msg) {
		return fail(RespCodeEnum.SYSTEM_ERROR.getCode(), msg);
	}

	public static <T> Result<T> fail(int code, String msg) {
		return new Result<T>()
			.code(code)
			.msg(msg);
	}

	public static <T> Result<T> fail(T data) {
		return new Result<T>()
			.code(RespCodeEnum.SYSTEM_ERROR.getCode())
			.msg(RespCodeEnum.SYSTEM_ERROR.getMsg())
			.data(data);
	}


	// 链式调用方法：设置状态码
	public Result<T> code(int code) {
		this.code = code;
		return this;
	}

	// 链式调用方法：设置响应消息
	public Result<T> msg(String msg) {
		this.msg = msg;
		return this;
	}

	// 链式调用方法：设置响应数据
	public Result<T> data(T data) {
		this.data = data;
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}


	public T getData() {
		return data;
	}
}
