package com.wangsl.device.dao;

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

	@Field("connected")
	private Boolean connected;

	@Field("client_id")
	private String clientId;

	@Field("keepalive")
	private Integer keepAlive;

	@Field("ipaddress")
	private String ipAddress;

	@Field("proto_ver")
	private Integer protoVer;

	@Field("connected_at")
	private Date connectedAt;

	@Field("disconnected_at")
	private Date disconnectedAt;

	@Field("conack")
	private Integer conAck;

	@Field("device_id")
	private ObjectId deviceId; // 关联设备 ID
}
