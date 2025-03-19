package com.wangsl.simulator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublishMessageParam {
	private String productKey;
	private String deviceName;
	private String message;
	private String dataType;
	private Integer qos;
}
