package com.wangsl.device.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "message")
@Builder
public class Message {
	@Id
	private ObjectId id;
	private Date sendAt; // 发送时间
	private Object payload; // 消息内容
	private String dataType; // 数据类型
	private ObjectId userId; // 隔离命名空间，productKey deviceName 只能确保同一用户下是唯一的
	private String productKey;
	private String deviceName;
	private ObjectId messageId; // 消息id 为了消息去重
	private String ipAddr; // 发送的ip
	private Integer qos; // 消息级别
	private String emqxNode; // emqx 节点名称
}
