package com.wangsl.device.manager;

import com.wangsl.common.web.Result;
import com.wangsl.device.dao.Connection;
import com.wangsl.device.dao.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conn")
public class ConnectionController {

	private final ConnectionRepository connectionRepository;

	@Autowired
	public ConnectionController(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	@GetMapping
	public Result<List<Connection>> getConnection() {
		List<Connection> connections = connectionRepository.findAll();
		return Result.success(connections);
	}
}
