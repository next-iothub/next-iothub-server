package com.wangsl.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.wangsl.common.api.Result;
import com.wangsl.common.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 授权失败的异常处理
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Result<Object> unauthorized = Result.unauthorized(null);
		String jsonString = JSON.toJSONString(unauthorized);
		WebUtil.renderString(response, jsonString);
	}
}
