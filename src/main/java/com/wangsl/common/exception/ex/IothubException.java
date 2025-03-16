package com.wangsl.common.exception.ex;

import com.wangsl.common.exception.CommonError;

public class IothubException extends RuntimeException{

	private String errMessage;

	public IothubException() {
	}

	public IothubException(String message) {
		super(message);
		this.errMessage = message;

	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public static void cast(String message){
		throw new IothubException(message);
	}

	public static void cast(CommonError error){
		throw new IothubException(error.getErrMessage());
	}

	public static void cast(IothubException iothubException){
		throw new IothubException(iothubException.getErrMessage());
	}

}
