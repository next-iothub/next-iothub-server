package com.wangsl.device.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDTO {

	private String deviceName; // 设备名称 唯一的
	private String nickName; // 备注名称
	private String productKey; // 产品key
}
