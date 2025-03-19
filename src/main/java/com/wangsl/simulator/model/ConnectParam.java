package com.wangsl.simulator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectParam {

	private String productKey;
	private String deviceName;
	private String secret;

}
