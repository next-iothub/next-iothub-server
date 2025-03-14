package com.wangsl.device.register;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthKey {

	private String productName;
	private String deviceName;
	private String secret;
}
