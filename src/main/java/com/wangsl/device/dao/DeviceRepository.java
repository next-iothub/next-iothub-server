package com.wangsl.device.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {

	@Query("{'productName': ?0, 'deviceName': ?1}")
	Device findDeviceByDeviceNameAndDeviceName(String productName, String deviceName);

	@Query("{'productName': ?0}")
	List<Device> findByProductName(String productName);

	@Query("{'username': ?0}")
	Device findByUsername(String username);
}
