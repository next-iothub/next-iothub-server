package com.wangsl.common.enums;

/**
 * 传输协议枚举
 */
public enum TransportProtocol {
	MQTT("mqtt", "MQTT协议"),
	HTTP("http", "HTTP协议"),
	COAP("coap", "CoAP协议"),
	WEBSOCKET("websocket", "WebSocket协议"),
	LWM2M("lwm2m", "LWM2M协议"),
	MODBUS("modbus", "Modbus协议"),
	OPC_UA("opc_ua", "OPC UA协议");

	private String code;
	private String description;

	TransportProtocol(String code, String description) {
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
