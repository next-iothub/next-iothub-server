package com.wangsl.device.emqx.hook;

import com.wangsl.common.exception.IothubExceptionEnum;
import com.wangsl.common.exception.ExceptionUtil;
import com.wangsl.device.emqx.model.PublishEventData;
import com.wangsl.device.model.Connection;
import com.wangsl.device.model.Device;
import com.wangsl.device.model.Message;
import com.wangsl.device.repository.ConnectionRepository;
import com.wangsl.device.repository.DeviceRepository;
import com.wangsl.device.repository.MessageRepository;
import com.wangsl.device.ws.DeviceStatusWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class HandleService {

	private final ConnectionRepository connectionRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusWebSocketHandler webSocketHandler;
	private final MessageRepository messageRepository;

	@Autowired
	public HandleService(ConnectionRepository connectionRepository, DeviceRepository deviceRepository, DeviceStatusWebSocketHandler webSocketHandler, MessageRepository messageRepository) {
		this.connectionRepository = connectionRepository;
		this.deviceRepository = deviceRepository;
		this.webSocketHandler = webSocketHandler;
		this.messageRepository = messageRepository;
	}


	public void handle(Map<String, Object> payload){
		// 处理 Webhook 事件
		String event = (String) payload.get("event");
		if ("client.connected".equals(event)) {
			// log.info(payload.toString());
			handleConnect(payload);
		} else if ("client.disconnected".equals(event)) {
			// log.info(payload.toString());
			handleDisconnect(payload);
		}
	}

	/**
	 * {
	 *   publish_received_at=1742384085211,
	 *   pub_props={
	 *     User-Property={
	 *
	 *     }
	 *   },
	 *   qos=1,
	 *   topic=data/client/test,
	 *   clientid=efl4rbBGDN30Mg.test_device6.67d7cda70613d7588ac4a17c,
	 *   client_attrs={
	 *
	 *   },
	 *   peerhost=127.0.0.1,
	 *   payload={
	 *     "status": "hello23"
	 *   },
	 *   username=efl4rbBGDN30Mg&test_device6,
	 *   event=message.publish,
	 *   metadata={
	 *     rule_id=publish_WH_D
	 *   },
	 *   peername=127.0.0.1: 43224,
	 *   timestamp=1742384085211,
	 *   node=emqx@127.0.0.1,
	 *   id=000630B068761963F4450000182C0007,
	 *   flags={
	 *     retain=false,
	 *     dup=false
	 *   }
	 * }
	 */
	/**
	 * 处理消息上行
	 * @param payload
	 */
	public void handlePublish(PublishEventData payload) {
		// 1.消息存储到 mongodb

		// 1.1 解析 topic 获取 productKey, deviceName, userId
		// topic=upload_data/S3DxOABy4DGxMq/tt_dev/String/67dac1a77206cb72290c1f32
		String topic = payload.getTopic();
		String[] topicParts = topic.split("/");
		String productKey = topicParts[1]; // upload_data/S3DxOABy4DGxMq/tt_dev/String/67dac1a77206cb72290c1f32
		String deviceName = topicParts[2];
		String dataType = topicParts[3];
		String messageId = topicParts[4];

		String clientId = payload.getClientid();
		String[] clientIdStrings = clientId.split("\\.");
		String userId = clientIdStrings[2];
		// 这里的userId 必须从clientId解析，因为是emqx webhook进行的请求，当前用户只能是 emqx
		// ObjectId userId1 = SecurityContextUtil.getCurrentUserId();
		// 2. 构建 Message 对象
		Message message = Message.builder()
			.sendAt(new Date(payload.getPublish_received_at())) // 使用 MQTT 消息的时间戳
			.payload(payload.getPayload()) // 消息内容
			.dataType(dataType) // 数据类型
			.userId(new ObjectId(userId)) // 用户 ID
			.productKey(productKey) // 产品 Key
			.deviceName(deviceName) // 设备名称
			.messageId(new ObjectId(messageId))// 消息 ID
			.ipAddr(payload.getPeername())
			.qos(payload.getQos())
			.emqxNode(payload.getNode())
			.build();

		// 3. 存储到 MongoDB
		messageRepository.save(message);

		// todo: 4. 将消息发送到 RabbitMQ
		// try {
		// 	String jsonMessage = objectMapper.writeValueAsString(message);
		// 	rabbitTemplate.convertAndSend("your_exchange_name", "your_routing_key", jsonMessage);
		// } catch (JsonProcessingException e) {
		// 	e.printStackTrace();
		// 	System.err.println("Failed to convert message to JSON: " + e.getMessage());
		// }

	}

	/**
	 * 处理连接断开
	 * @param payload
	 */
	private void handleDisconnect(Map<String, Object> payload){
		// 1.通过clientId查找最新的连接
		String clientId = (String) payload.get("clientid");
		Optional<Connection> topConnection = connectionRepository.findTopByClientIdAndDisconnectedAtIsNullOrderByConnectedAtDesc(clientId);
		// 2.设置离线时间和连接状态
		topConnection.ifPresent(connection -> {
			connection.setDisconnectedAt(new Date());
			connection.setStatus(false);
			connectionRepository.save(connection);
		});

		// websocket 实时推送
		try {
			webSocketHandler.sendStatusUpdate(clientId, "offline");
		} catch (IOException e) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_DEVICE_STATUS);
		}

		// 3.修改设备信息
		String[] split = clientId.split("\\.");
		String productKey = split[0];
		String deviceName = split[1];
		Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndProductKey(deviceName, productKey);
		optionalDevice.ifPresent(device -> {
				device.setStatus("offline");
				deviceRepository.save(device);
			}
		);

	}

	/**
	 * 处理连接
	 * @param payload
	 */
	private void handleConnect(Map<String, Object> payload) {
		String clientId = (String) payload.get("clientid");
		String ipAddr = (String) payload.get("peername");
		Integer keepalive = (Integer) payload.get("keepalive");

		// 创建连接并保存
		Connection connection = Connection.builder()
			.clientId(clientId)
			.ipAddress(ipAddr)
			.keepalive(keepalive)
			.connectedAt(new Date())
			.status(true)
			.build();
		connectionRepository.save(connection);

		// websocket 实时推送
		try {
			webSocketHandler.sendStatusUpdate(clientId, "online");
		} catch (IOException e) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_DEVICE_STATUS);
		}

		// 修改设备信息
		String[] split = clientId.split("\\.");
		String productKey = split[0];
		String deviceName = split[1];
		Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndProductKey(deviceName, productKey);
		optionalDevice.ifPresent(device -> {
			device.setLastOnlineTime(connection.getConnectedAt());
			device.setStatus("online");
			deviceRepository.save(device);
		});

	}

}
