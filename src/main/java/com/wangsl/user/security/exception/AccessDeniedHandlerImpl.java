package com.wangsl.user.security.exception;

import com.alibaba.fastjson.JSON;
import com.wangsl.common.web.Result;
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
		Result<String> result = Result.fail("无权限");
		String jsonString = JSON.toJSONString(result);
		WebUtil.renderString(response, jsonString);
	}
}
