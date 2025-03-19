package com.wangsl.simulator.util;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

@Slf4j
public class MqttUtil {
	/**
	 * 向指定主题发布消息
	 *
	 * @param mqttClient      mqtt客户端
	 * @param topic      发布消息的主题
	 * @param message    消息内容
	 * @param qos        消息的QoS级别（0, 1, 2）
	 */
	public static void publishMessage(MqttClient mqttClient, String topic, String message, int qos) {

		try {
			// 创建消息并设置QoS
			MqttMessage mqttMessage = new MqttMessage(message.getBytes());
			mqttMessage.setQos(qos);

			// 发布消息
			mqttClient.publish(topic, mqttMessage);
			log.info("消息发布成功！主题: {}, 内容: {}", topic, message);

		} catch (MqttException e) {
			log.info("消息发布失败: {}", e.getMessage());
		}
	}
}
