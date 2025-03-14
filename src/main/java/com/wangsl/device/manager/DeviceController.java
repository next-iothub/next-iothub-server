package com.wangsl.device.manager;

import com.wangsl.common.web.Result;
import com.wangsl.device.dao.Connection;
import com.wangsl.device.dao.ConnectionRepository;
import com.wangsl.device.dao.Device;
import com.wangsl.device.dao.DeviceRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {

	private final DeviceRepository deviceRepository;

	private final ConnectionRepository connectionRepository;

	private final MongoTemplate mongoTemplate;

	@Autowired
	public DeviceController(DeviceRepository deviceRepository, ConnectionRepository connectionRepository, MongoTemplate mongoTemplate) {
		this.deviceRepository = deviceRepository;
		this.connectionRepository = connectionRepository;
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 获取全部的设备设备信息
	 * @return
	 */
	@GetMapping("/list")
	public Result<List<Device>> list() {
		List<Device> devices = deviceRepository.findAll();
		return Result.success(devices);
	}

	/**
	 * 根据产品名和设备名获取设备详细信息
	 * @param productName
	 * @param deviceName
	 * @return
	 */
	@GetMapping("/{productName}/{deviceName}")
	public Result<DeviceDetailsDto> getDeviceInfo(@PathVariable String productName, @PathVariable String deviceName) {
		Device device = deviceRepository
			.findDeviceByDeviceNameAndDeviceName(productName, deviceName);
		List<Connection> connections = connectionRepository.findByDeviceId(device.getId());
		DeviceDetailsDto deviceDetailsDto = DeviceDetailsDto.builder().device(device).connections(connections).build();
		return Result.success(deviceDetailsDto);
	}

	/**
	 * 根据产品名称获取该产品下的设备列表
	 * @param productName
	 * @return
	 */
	@GetMapping("/{productName}")
	public Result<List<Device>> getProductList(@PathVariable String productName) {
		List<Device> devices = deviceRepository.findByProductName(productName);
		return Result.success(devices);
	}

	/**
	 * 获取产品数量
	 * @return
	 */
	@GetMapping("/product/count")
	public Result<Long> getProductCount(){
		Aggregation aggregation = Aggregation.newAggregation(
			Aggregation.group("productName"),
			Aggregation.count().as("distinctProductCount")
		);
		AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "device", Document.class);
		Document document = (Document) results.getRawResults().get("results", ArrayList.class).get(0);
		Long count = (long) (results.getRawResults().isEmpty() ? 0 : document.getInteger("distinctProductCount"));;
		return Result.success(count);
	}

	/**
	 * 获取设备数量
	 * @return
	 */
	@GetMapping("/device/count")
	public Result<Map<String, Object>> getDeviceCount(){
		long count = deviceRepository.count();
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		return Result.success(map);
	}

	@GetMapping("/delall")
	public Result<String> delAll(){
		deviceRepository.deleteAll();
		connectionRepository.deleteAll();
		return Result.success();
	}
}
