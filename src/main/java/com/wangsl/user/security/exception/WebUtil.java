package com.wangsl.user.security.exception;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtil {

	public static void renderString(HttpServletResponse response, String string){
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(string);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
