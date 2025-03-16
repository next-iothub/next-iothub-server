package com.wangsl.common.exception.ex;

public class UnauthorizedException extends IothubException {
	public UnauthorizedException() {
		super("未认证");
	}
}
