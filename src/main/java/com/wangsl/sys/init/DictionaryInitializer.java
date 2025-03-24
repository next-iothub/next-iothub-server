package com.wangsl.sys.init;

import com.wangsl.sys.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class DictionaryInitializer implements CommandLineRunner {


	private final MongoTemplate mongoTemplate;

	@Autowired
	public DictionaryInitializer(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		// 清空现有数据字典
		mongoTemplate.dropCollection("dictionaries");

		// 插入初始数据字典
		mongoTemplate.insertAll(Arrays.asList(
			// 产品类型
			new Dictionary(null, "product_type", "direct", "设备", 1, "普通设备", true, null, null, new Date(), new Date()),
			new Dictionary(null,"product_type", "gateway", "网关", 2, "网关设备", true, null, null, new Date(), new Date()),
			new Dictionary(null,"product_type", "edge", "边缘设备", 3, "边缘计算设备", true, null, null, new Date(), new Date()),

			// 网络类型
			new Dictionary(null,"network_type", "wifi", "Wi-Fi", 1, "Wi-Fi 网络", true, null, null, new Date(), new Date()),
			new Dictionary(null,"network_type", "cellular", "蜂窝网络", 2, "蜂窝网络", true, null, null, new Date(), new Date()),
			new Dictionary(null,"network_type", "ethernet", "以太网", 3, "以太网", true, null, null, new Date(), new Date()),
			new Dictionary(null,"network_type", "zigbee", "Zigbee", 4, "Zigbee 网络", true, null, null, new Date(), new Date()),
			new Dictionary(null,"network_type", "lora", "LoRa", 5, "LoRa 网络", true, null, null, new Date(), new Date()),

			// 协议类型
			new Dictionary(null,"protocol_type", "mqtt", "MQTT", 1, "MQTT 协议", true, null, null, new Date(), new Date()),
			new Dictionary(null,"protocol_type", "coap", "CoAP", 2, "CoAP 协议", true, null, null, new Date(), new Date()),
			new Dictionary(null,"protocol_type", "http", "HTTP", 3, "HTTP 协议", true, null, null, new Date(), new Date()),
			new Dictionary(null,"protocol_type", "modbus", "Modbus", 4, "Modbus 协议", true, null, null, new Date(), new Date()),

			// 认证类型
			new Dictionary(null,"auth_type", "secret", "密钥认证", 1, "使用密钥进行认证", true, null, null, new Date(), new Date()),
			new Dictionary(null,"auth_type", "certificate", "证书认证", 2, "使用证书进行认证", true, null,null,  new Date(), new Date()),
			new Dictionary(null,"auth_type", "token", "令牌认证", 3, "使用令牌进行认证", true, null, null, new Date(), new Date()),

			// 数据格式
			new Dictionary(null,"data_format", "json", "JSON", 1, "JSON 数据格式", true, null, null, new Date(), new Date()),
			new Dictionary(null,"data_format", "custom", "自定义格式", 2, "自定义数据格式", true, null, null, new Date(), new Date()),
			new Dictionary(null,"data_format", "binary", "二进制格式", 3, "二进制数据格式", true, null, null, new Date(), new Date()),

			// 产品状态
			new Dictionary(null,"status", "draft", "草稿", 1, "产品处于草稿状态", true, null, null, new Date(), new Date()),
			new Dictionary(null,"status", "testing", "测试中", 2, "产品处于测试阶段", true, null, null, new Date(), new Date()),
			new Dictionary(null,"status", "published", "已发布", 3, "产品已发布", true, null, null, new Date(), new Date()),
			new Dictionary(null,"status", "deprecated", "已弃用", 4, "产品已弃用", true, null, null, new Date(), new Date()),

			// 节点类型
			new Dictionary(null,"node_type", "direct", "直连设备", 1, "设备直接连接到平台", true, null, null, new Date(), new Date()),
			new Dictionary(null,"node_type", "gateway", "网关设备", 2, "设备通过网关连接到平台", true, null, null, new Date(), new Date())
		));
	}
}
