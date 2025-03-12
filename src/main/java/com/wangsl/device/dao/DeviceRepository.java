package com.wangsl.device.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

	// 仅返回 productName、deviceName、secret
	@Query(value = "{}", fields = "{'productName': 1, 'deviceName': 1, 'secret': 1}")
	List<Device> findAllDevices();
}
