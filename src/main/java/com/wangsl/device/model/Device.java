package com.wangsl.device.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "device")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

	@Id
	private ObjectId id;  // MongoDB 自动生成的唯一ID
	private String username; // mqtt 用户名
	private String password; // mqtt 密码
	private String token; // 临时token
	private String productName; // 产品名
	private String deviceName; // 设备名
	private String secret; // 设备密钥
	private Boolean active; // 设备状态 true可用

}
