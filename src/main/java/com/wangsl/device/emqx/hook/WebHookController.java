package com.wangsl.device.emqx.hook;

import com.mongodb.event.ConnectionReadyEvent;
import com.wangsl.common.web.Result;
import com.wangsl.device.dao.Connection;
import com.wangsl.device.dao.ConnectionRepository;
import com.wangsl.device.dao.Device;
import com.wangsl.device.dao.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.CopyOnWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/emqx")
public class WebHookController {

	private final DeviceRepository deviceRepository;
	private final ConnectionRepository connectionRepository;

	@Autowired
	public WebHookController(DeviceRepository deviceRepository, ConnectionRepository connectionRepository) {
		this.deviceRepository = deviceRepository;
		this.connectionRepository = connectionRepository;
	}


	@PostMapping("/hook")
	public Result<String> hook(@RequestBody Map<String, Object> payload) {
		log.info(payload.toString());
		// 处理 Webhook 事件
		String event = (String) payload.get("event");
		if ("client.connected".equals(event)) {
			handleConnect(payload);
		} else if ("client.disconnected".equals(event)) {
			handleDisconnect(payload);
		}

		return Result.success();
	}

	/**
	 * 处理连接断开
	 * @param payload
	 */
	private void handleDisconnect(Map<String, Object> payload) {
		// 1.通过username的productName 和 deviceName 查找device(包含connection列表)
		Connection connection = getConnection(payload);
		// 2.用clientid 从connection列表查找connection
		// 3.修改状态为 disconnected
		connection.setConnected(false);
		connection.setDisconnectedAt(new Date((Long) payload.get("disconnected_at")));
		connectionRepository.save(connection);
	}

	private Connection getConnection(Map<String, Object> payload) {
		String username = (String) payload.get("username");
		String clientId = (String) payload.get("clientid");
		Device device = deviceRepository.findByUsername(username);
		return connectionRepository.findByDeviceIdAndClientId(device.getId(), clientId);
	}

	/**
	 * 处理连接
	 * @param payload
	 */
	private void handleConnect(Map<String, Object> payload){
		// 1.通过username查找device(包含connection列表)
		String username = (String) payload.get("username");
		String clientId = (String) payload.get("clientid");
		Device device = deviceRepository.findByUsername(username);
		Connection connection = connectionRepository.findByDeviceIdAndClientId(device.getId(), clientId);

		// 2.用clientid 从connection列表查找connection
		//  2.1.不存在则创建connection
		if(connection == null){ // 不存在连接信息则创建
			connection = Connection.builder()
				.connected(true) // 设为已连接
				.clientId(clientId) // 获取 clientid
				.keepAlive((Integer) payload.get("keepalive")) // 获取 keepalive
				.ipAddress(payload.get("peername").toString()) // 取对端 IP
				.protoVer((Integer) payload.get("proto_ver")) // 获取协议版本
				.connectedAt(new Date((Long) payload.get("connected_at"))) // 连接时间
				.conAck(1) // 假设连接成功返回 1（可根据情况调整）
				.deviceId(device.getId())
				.build();
		}
		//  2.2.存在则更改状态为 connected
		connection.setConnected(true);

		// 3.回写数据
		connectionRepository.save(connection);
	}


	@GetMapping("/hook")
	public Result<String> getHook() {
		log.info("emqx hook");
		return Result.success();
	}


}
