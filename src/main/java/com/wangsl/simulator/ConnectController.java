package com.wangsl.simulator;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/simulator")
public class ConnectController {

	private final MqttConnect mqttConnect;

	@Autowired
	public ConnectController(MqttConnect mqttConnect) {
		this.mqttConnect = mqttConnect;
	}

	@PostMapping("/connect")
	public Map<String, Object> deviceConnect(@RequestBody ConnectParam param) {

		mqttConnect.connect(param.getPassword(), param.getUsername(), param.getPassword());

		Map<String, Object> res = new HashMap<>();
		res.put("status", 0);
		res.put("message", "connect successfully");
		return res;
	}
}
