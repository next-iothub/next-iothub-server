package com.wangsl.device.dao;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "users")
@Builder
public class Device {

	@Id
	private String id;  // MongoDB 自动生成的唯一ID
	private String username; // mqtt 用户名
	private String password; // mqtt 密码
	private String token; // 临时token
	private String productName; // 产品名
	private String deviceName; // 设备名
	private String secret; // 设备密钥

}
