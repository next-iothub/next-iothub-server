package com.wangsl.device.emqx.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PublishEventData {
	private long publish_received_at; // 消息接收时间戳
	private Map<String, Object> pub_props; // 发布属性
	private int qos; // QoS 级别
	private String topic; // 主题
	private String clientid; // 客户端 ID
	private Map<String, Object> client_attrs; // 客户端属性
	private String peerhost; // 客户端 IP
	private Object payload; // 消息内容
	private String username; // 用户名
	private String event; // 事件类型
	private Map<String, String> metadata; // 元数据
	private String peername; // 客户端 IP 和端口
	private long timestamp; // 时间戳
	private String node; // Broker 节点
	private String id; // 消息 ID
	private Map<String, Boolean> flags; // 标志位
}
