package com.wangsl.common.exception;

import com.wangsl.common.exception.ex.IothubException;
import com.wangsl.common.web.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(IothubException.class)
	public Result<RestErrorResponse> handleIothubException(Exception ex, WebRequest request) {
		return Result.fail(
			RestErrorResponse.builder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.exMsg(ex.getMessage())
				.build()
		);
	}

	@ExceptionHandler(Exception.class)
	public Result<RestErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
		return Result.fail(
			RestErrorResponse.builder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.exMsg(CommonError.UNKNOWN_ERROR.getErrMessage())
				.build()
		);
	}
}
