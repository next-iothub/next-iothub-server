package com.wangsl.user.security.exception;

import com.alibaba.fastjson.JSON;
import com.wangsl.common.exception.CommonError;
import com.wangsl.common.exception.RestErrorResponse;
import com.wangsl.common.web.model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证失败的异常处理
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Result<RestErrorResponse> result = Result.fail(
			RestErrorResponse.builder()
				.code(HttpStatus.UNAUTHORIZED.value())
				.exMsg(CommonError.UNAUTHORIZED.getErrMessage())
				.build()
		);
		String jsonString = JSON.toJSONString(result);
		WebUtil.renderString(response, jsonString);
	}
}
