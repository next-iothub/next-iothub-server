package com.wangsl.simulator;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.packet.MqttConnAck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备模拟器核心类
 * 1. 使用Paho MQTT客户端实现MQTT的连接
 * 2. 使用ConcurrentHashMap维护连接状态
 */
@Service
public class ConnectService {

	@Value("${emqx.url}")
	private String MQTT_BROKER_URL;
	private final Map<String, MqttClient> clientMap = new ConcurrentHashMap<>(); // 维护设备连接状态

	/**
	 * 连接设备
	 * @param param
	 * @return
	 */
	public boolean connect(ConnectParam param) {
		try {
			// 创建连接
			String clientId = param.getClientId();
			MqttClient client = new MqttClient(MQTT_BROKER_URL, clientId, new MemoryPersistence());

			// 连接参数
			MqttConnectionOptions options = new MqttConnectionOptions();
			options.setUserName(param.getUsername());
			options.setPassword(param.getPassword().getBytes());
			options.setCleanStart(false);
			options.setAutomaticReconnect(true);

			// 连接到 Broker
			IMqttToken iMqttToken = client.connectWithResult(options);
			MqttConnAck connAck = (MqttConnAck) iMqttToken.getResponse();

			int returnCode = connAck.getReturnCode();
			boolean sessionPresent = connAck.getSessionPresent();

			// 判断连接是否成功
			if (returnCode == 0) {
				System.out.println("Connected successfully. Return code: " + returnCode + ", sessionPresent: " + sessionPresent);
				// 保存设备连接
				clientMap.put(clientId, client);
				return true;
			} else {
				System.out.println("Connection failed. Return code: " + returnCode + ", sessionPresent: " + sessionPresent);
				return false;
			}

		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * token 连接
	 * @return
	 */
	public boolean connect(String token) {
		try {
			String clientId = UUID.randomUUID().toString();
			MqttClient client = new MqttClient(MQTT_BROKER_URL, clientId, new MemoryPersistence());

			// 连接参数
			MqttConnectionOptions options = new MqttConnectionOptions();
			options.setPassword(token.getBytes());
			options.setCleanStart(true);
			options.setAutomaticReconnect(true);

			// 连接到 Broker
			IMqttToken iMqttToken = client.connectWithResult(options);
			MqttConnAck connAck = (MqttConnAck) iMqttToken.getResponse();

			int returnCode = connAck.getReturnCode();
			boolean sessionPresent = connAck.getSessionPresent();


			System.out.println("return code: " + returnCode + ", sessionPresent: " + sessionPresent);


			clientMap.put(token, client); // 保存设备连接
			return true;
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
	}

		/**
		 * 断开设备连接
		 * @param param
		 * @return
		 */
	public boolean disConnect(ConnectParam param) {
		try {
			String clientId = param.getClientId();
			MqttClient client = clientMap.get(clientId);
			if (client != null && client.isConnected()) {
				client.disconnect();
				clientMap.remove(clientId);
				return true;
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return false;
	}
}
