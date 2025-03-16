package com.wangsl.common.exception.ex;

public class ForbiddenException extends IothubException {
	public ForbiddenException() {
		super("无权访问");
	}
}
