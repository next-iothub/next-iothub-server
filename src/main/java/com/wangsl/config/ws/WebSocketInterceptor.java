package com.wangsl.config.ws;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
	                               WebSocketHandler wsHandler, Map<String, Object> attributes) {
		if (request instanceof ServletServerHttpRequest servletRequest) {
			// 获取 deviceId 参数
			String deviceId = servletRequest.getServletRequest().getParameter("deviceId");
			if (deviceId != null) {
				attributes.put("deviceId", deviceId); // 存入 session attributes
				System.out.println("设备ID：" + deviceId);
			}
		}
		return true; // 允许握手
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
	                           WebSocketHandler wsHandler, Exception exception) {
		// 握手完成后可执行的操作（可选）
	}
}
