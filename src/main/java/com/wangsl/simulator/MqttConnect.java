package com.wangsl.simulator;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.packet.MqttConnAck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttConnect {

	@Value("${emqx.url}")
	public String BROKER_URL;

	public static final String CLIENT_ID = "temp_005";

	public MqttClient connect(String clientId, String username, String password){
		try {
			// 创建 MQTT 客户端
			MqttClient client = new MqttClient(BROKER_URL, clientId, new MemoryPersistence());

			// MQTT 连接选项
			MqttConnectionOptions options = new MqttConnectionOptions();
			options.setCleanStart(true); // 设置持久会话
			options.setAutomaticReconnect(true); // 自动重连
			options.setSessionExpiryInterval(60L); // 让服务器保存会话（秒）
			options.setUserName(username);
			options.setPassword(password.getBytes());

			// 连接到 Broker
			IMqttToken iMqttToken = client.connectWithResult(options);
			MqttConnAck connAck = (MqttConnAck) iMqttToken.getResponse();
			int returnCode = connAck.getReturnCode();
			boolean sessionPresent = connAck.getSessionPresent();
			System.out.println("连接成功");
			System.out.println("return code: " + returnCode + ", sessionPresent: " + sessionPresent);

			return client;

			// // 阻塞主线程，保持连接
			// Thread.sleep(60000);
			//
			// // 断开连接
			// client.disconnect();
			// System.out.println("连接断开");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
