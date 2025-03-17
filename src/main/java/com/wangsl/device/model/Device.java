package com.wangsl.device.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "device")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

	@Id
	private ObjectId id;  // MongoDB 自动生成的唯一ID
	private ObjectId productId; // 产品id
	private String productKey; // 产品key
	private String deviceName; // 设备名称 唯一的
	private String nickName; // 备注名称
	private String status; // 设备状态
	private Date createTime; // 创建时间
	private Date lastOnlineTime; // 上次在线时间
	private String deviceSecret; // 设备密钥
	private String username; // mqtt 用户名（）
	private String password; // mqtt 密码
	private String clientId; // 客户端id
	private Boolean active; // 设备是否启用

}
