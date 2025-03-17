package com.wangsl.device.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "connection")
@Builder
public class Connection {
	@Id
	private ObjectId id;
	private ObjectId deviceId; // 关联设备 ID
	private String clientId; // 连接客户端id
	private String status; // 连接状态
	private String ipAddress; // ip地址
	private Date connectedAt; // 连接时间
	private Date disconnectedAt; // 下线时间

}
