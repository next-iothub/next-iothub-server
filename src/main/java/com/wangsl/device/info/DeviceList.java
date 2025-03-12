package com.wangsl.device.info;

import com.wangsl.device.dao.Device;
import com.wangsl.device.dao.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

@RestController
@RequestMapping("/device")
public class DeviceList {

	private final DeviceRepository deviceRepository;

	@Autowired
	public DeviceList(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}

	@GetMapping("/list")
	public Map<String, Object> list() {
		// todo: 连接 mongodb 无法查询
		List<Device> devices = deviceRepository.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("deviceList", devices);
		return map;
	}
}
