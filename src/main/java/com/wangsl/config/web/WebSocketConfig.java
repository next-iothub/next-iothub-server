package com.wangsl.config.web;

import com.wangsl.device.ws.DeviceStatusWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final DeviceStatusWebSocketHandler deviceStatusWebSocketHandler;
	private final WebSocketInterceptor webSocketInterceptor;

	@Autowired
	public WebSocketConfig(DeviceStatusWebSocketHandler deviceStatusWebSocketHandler, WebSocketInterceptor webSocketInterceptor) {
		this.deviceStatusWebSocketHandler = deviceStatusWebSocketHandler;
		this.webSocketInterceptor = webSocketInterceptor;
	}

	// todo: websocket 无法连接
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(deviceStatusWebSocketHandler, "/ws/device/status")
			.addInterceptors(webSocketInterceptor)
			.setAllowedOrigins("*"); // 允许跨域
	}
}
