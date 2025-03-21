package com.wangsl.simulator;

import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.simulator.model.ConnectParam;
import com.wangsl.simulator.model.PublishMessageParam;
import com.wangsl.simulator.util.MqttUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
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
@Slf4j
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
			ObjectId userId = SecurityContextUtil.getCurrentUserId();
			// 创建连接
			String clientId = param.getProductKey() + "." + param.getDeviceName() + "." + userId.toString();
			if (clientMap.containsKey(clientId)) {
				MqttClient client = clientMap.get(clientId);
				if(!client.isConnected())
					client.reconnect();
				return true;
			}
			

			// 获取当前项目的根目录
			String projectRoot = System.getProperty("user.dir");

			// 设置 MQTT 持久化数据的保存路径
			String persistencePath = projectRoot + "/mqtt";
			MqttClient client = new MqttClient(MQTT_BROKER_URL, clientId, new MqttDefaultFilePersistence(persistencePath));
			// 连接参数
			MqttConnectionOptions options = new MqttConnectionOptions();
			options.setUserName(param.getProductKey() + "&" + param.getDeviceName());
			options.setPassword(param.getSecret().getBytes());
			options.setCleanStart(false);
			options.setSessionExpiryInterval(60 * 60L);
			options.setAutomaticReconnect(true);

			// 连接到 Broker
			IMqttToken iMqttToken = client.connectWithResult(options);
			MqttConnAck connAck = (MqttConnAck) iMqttToken.getResponse();

			int returnCode = connAck.getReturnCode();
			boolean sessionPresent = connAck.getSessionPresent();

			// 判断连接是否成功
			if (returnCode == 0) {
				log.info("Connected successfully. Return code: {}, sessionPresent: {}", returnCode, sessionPresent);
				// 保存设备连接
				clientMap.put(clientId, client);
				return true;
			} else {
				log.info("Connection failed. Return code: {}, sessionPresent: {}", returnCode, sessionPresent);
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

			ObjectId userId = SecurityContextUtil.getCurrentUserId();
			// 创建连接
			String clientId = param.getProductKey() + "." + param.getDeviceName() + "." + userId.toString();
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

	/**
	 * 上行数据
	 * @param param
	 * @return
	 */
	public boolean uploadData(PublishMessageParam param) {
		// 需要判断是否连接
		String clientId = calcClientId(param.getProductKey(), param.getDeviceName());
		if(clientMap.containsKey(clientId)){
			MqttClient client = clientMap.get(clientId);
			String messageId = generateMsgId();
			final String uploadDataTopic = "upload_data/"
				+ param.getProductKey()
				+ "/" + param.getDeviceName()
				+ "/" + param.getDataType()
				+ "/" + messageId;
			MqttUtil.publishMessage(client, uploadDataTopic, param.getMessage(), param.getQos());
			return true;
		}
		return false;
	}

	/**
	 * 生成消息id
	 * @return
	 */
	private String generateMsgId() {
		return ObjectId.get().toHexString();
	}

	/**
	 * 获取clientid
	 * @param productKey
	 * @param deviceName
	 * @return
	 */
	private String calcClientId(String productKey, String deviceName){
		ObjectId userId = SecurityContextUtil.getCurrentUserId();
		return productKey + "." + deviceName + "." + userId.toString();
	}
}
