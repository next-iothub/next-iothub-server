package com.wangsl.device.emqx.hook;

import com.wangsl.common.exception.ex.IothubException;
import com.wangsl.device.model.Connection;
import com.wangsl.device.model.Device;
import com.wangsl.device.repository.ConnectionRepository;
import com.wangsl.device.repository.DeviceRepository;
import com.wangsl.device.ws.DeviceStatusWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
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

	@Autowired
	public HandleService(ConnectionRepository connectionRepository, DeviceRepository deviceRepository, DeviceStatusWebSocketHandler webSocketHandler) {
		this.connectionRepository = connectionRepository;
		this.deviceRepository = deviceRepository;
		this.webSocketHandler = webSocketHandler;
	}


	public void handle(Map<String, Object> payload){
		// 处理 Webhook 事件
		String event = (String) payload.get("event");
		if ("client.connected".equals(event)) {
			log.info(payload.toString());
			handleConnect(payload);
		} else if ("client.disconnected".equals(event)) {
			log.info(payload.toString());
			handleDisconnect(payload);
		}
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
			IothubException.cast("设备状态实时推送失败");
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
			IothubException.cast("设备状态实时推送失败");
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
