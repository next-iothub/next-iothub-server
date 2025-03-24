package com.wangsl.common.enums;

/**
 * 网络类型枚举
 */
public enum NetworkType {
	WIFI("wifi", "WiFi"),
	CELLULAR_2G("2g", "2G蜂窝网络"),
	CELLULAR_3G("3g", "3G蜂窝网络"),
	CELLULAR_4G("4g", "4G蜂窝网络"),
	CELLULAR_5G("5g", "5G蜂窝网络"),
	ETHERNET("ethernet", "以太网"),
	LORAWAN("lorawan", "LoRaWAN"),
	ZIGBEE("zigbee", "ZigBee"),
	BLUETOOTH("bluetooth", "蓝牙"),
	NB_IOT("nb_iot", "NB-IoT"),
	OTHER("other", "其他");

	private String code;
	private String description;

	NetworkType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
