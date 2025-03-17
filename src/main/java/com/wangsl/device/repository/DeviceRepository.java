package com.wangsl.device.repository;

import com.wangsl.device.model.Device;
import com.wangsl.device.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {

	Optional<Device> findByDeviceNameAndProductKey(String deviceName, String productKey);



	// 根据 productKey 进行分页查询
	Page<Device> findByProductKey(String productKey, Pageable pageable);

}
