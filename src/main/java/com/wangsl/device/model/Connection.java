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
	private String clientId; // 连接客户端id
	private Boolean status; // 连接状态 true 在线，false 离线
	private String ipAddress; // ip地址
	private Date connectedAt; // 连接时间
	private Date disconnectedAt; // 下线时间
	private Integer keepalive; // 保活时间

}
