package com.wangsl.device.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 产品实体类
 * 对应MongoDB中的products集合

 */
@Data
@Document(collection = "products")
public class Product {
	@Id
	private String id;
	// 产品唯一标识符
	@Indexed(unique = true)
	private String productKey;
	private String productId;
	private String name;
	private String description;
	private String category;
	private List<String> categoryPath;
	private String model;
	private String manufacturer;

	// 用于数据分区的用户ID
	private String userId;

	// 产品基本属性
	private String productType;  // device, gateway, edge
	private String networkType;  // wifi, cellular, ethernet, zigbee, lora, etc.
	private String protocolType; // mqtt, coap, http, modbus, etc.
	private String authType;     // secret, certificate, token
	private String dataFormat;   // json, custom, binary
	private String status;       // draft, testing, published, deprecated
	private List<String> tags; // 标签
	private String nodeType;     // direct, gateway
	private Boolean dataEncryption; // 是否启用数据加密

	// 产品功能特性
	private Features features;

	// 设备统计
	private DeviceCount deviceCount;

	// 物模型定义
	private ThingModel thingModel;

	// 产品配置
	private Configurations configurations;

	private Date createdAt;
	private Date updatedAt;

	/**
	 * 产品功能特性
	 */
	@Data
	@Builder
	public static class Features {
		private Boolean otaSupport;      // 是否支持OTA
		private Boolean groupControl;    // 是否支持分组控制
		private Boolean localControl;    // 是否支持本地控制
		private Boolean sceneAutomation; // 是否支持场景自动化
		private Boolean edgeComputing;   // 是否支持边缘计算
	}

	/**
	 * 设备统计
	 */
	@Data
	public static class DeviceCount {
		private Integer total;
		private Integer active;
		private Integer online;
	}

	/**
	 * 物模型定义
	 */
	@Data
	@Builder
	public static class ThingModel {
		private List<Property> properties;
		private List<Event> events;
		private List<Service> services;

		/**
		 * 物模型属性
		 */
		@Data
		public static class Property {
			private String id;
			private String name;
			private String dataType;
			private String unit;
			private Double min;
			private Double max;
			private Double step;
			private String mode;  // r(只读), w(只写), rw(读写)
			private Boolean required;
			private Map<String, String> specs; // 枚举类型的规格定义
		}

		/**
		 * 物模型事件
		 */
		@Data
		public static class Event {
			private String id;
			private String name;
			private String type;  // info, alert, error
			private List<Parameter> params;

			/**
			 * 事件参数
			 */
			@Data
			public static class Parameter {
				private String id;
				private String name;
				private String dataType;
			}
		}

		/**
		 * 物模型服务
		 */
		@Data
		public static class Service {
			private String id;
			private String name;
			private List<Parameter> input;
			private List<Parameter> output;

			/**
			 * 服务参数
			 */
			@Data
			public static class Parameter {
				private String id;
				private String name;
				private String dataType;
				private Map<String, String> specs; // 枚举类型的规格定义
			}
		}
	}

	/**
	 * 产品配置
	 */
	@Data
	@Builder
	public static class Configurations {
		private MqttTopic mqttTopic;
		private DefaultSettings defaultSettings;
		private NetworkSettings networkSettings;

		/**
		 * MQTT主题配置
		 */
		@Data
		public static class MqttTopic {
			private TopicConfig uplink;
			private TopicConfig downlink;

			/**
			 * 主题配置
			 */
			@Data
			public static class TopicConfig {
				private String property;
				private String event;
				private String service;
			}
		}

		/**
		 * 默认设置
		 */
		@Data
		public static class DefaultSettings {
			private Integer reportInterval;
			private Boolean alarmEnabled;
		}

		/**
		 * 网络设置
		 */
		@Data
		public static class NetworkSettings {
			private Integer reconnectInterval;
			private Integer keepAliveTimeout;
		}
	}
}
