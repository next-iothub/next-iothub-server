package com.wangsl.common.enums;

/**
 * 设备类别枚举
 */
public enum DeviceCategory {
	SENSOR("sensor", "传感器"),
	CONTROLLER("controller", "控制器"),
	GATEWAY("gateway", "网关"),
	CAMERA("camera", "摄像头"),
	DISPLAY("display", "显示设备"),
	WEARABLE("wearable", "可穿戴设备"),
	HOME_APPLIANCE("home_appliance", "家用电器"),
	INDUSTRIAL_EQUIPMENT("industrial_equipment", "工业设备"),
	ROBOT("robot", "机器人"),
	VEHICLE("vehicle", "车辆"),
	OTHER("other", "其他");

	private String code;
	private String description;

	DeviceCategory(String code, String description) {
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
