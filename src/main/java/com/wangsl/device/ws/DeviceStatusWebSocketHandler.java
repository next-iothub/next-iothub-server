package com.wangsl.device.ws;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * const socket = new WebSocket('ws://localhost:8080/ws/device/status');
 *
 * // 连接成功
 * socket.onopen = () => {
 *   console.log('WebSocket 连接成功');
 *   // 发送订阅设备状态请求
 *   socket.send(JSON.stringify({ deviceId: 'device001' }));
 * };
 *
 * // 接收消息
 * socket.onmessage = (event) => {
 *   console.log('设备状态更新:', event.data);
 * };
 *
 * // 连接关闭
 * socket.onclose = () => {
 *   console.log('WebSocket 连接关闭');
 * };
 *
 * // 错误处理
 * socket.onerror = (error) => {
 *   console.error('WebSocket 错误:', error);
 * };
 */
@Service
public class DeviceStatusWebSocketHandler extends TextWebSocketHandler {

	// 保存设备ID与 WebSocket 会话的映射关系
	private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String deviceId = (String) session.getAttributes().get("deviceId"); // 前端连接时定义的
		if (deviceId != null) {
			sessionMap.put(deviceId, session);
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 接收客户端消息 (可选)
		String payload = message.getPayload();
		System.out.println("收到消息: " + payload);
	}

	// 关闭
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessionMap.values().remove(session);
	}

	// 推送设备状态更新
	public void sendStatusUpdate(String deviceId, String status) throws IOException {
		WebSocketSession session = sessionMap.get(deviceId);
		if (session != null && session.isOpen()) {
			session.sendMessage(new TextMessage(status));
		}
	}

}
