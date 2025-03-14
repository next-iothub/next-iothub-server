package com.wangsl.device.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface ConnectionRepository extends MongoRepository<Connection, ObjectId>{

	@Query("{'device_id': ?0, 'client_id': ?1}")
	Connection findByDeviceIdAndClientId(ObjectId deviceId, String clientId);

	@Query("{'device_id': ?0}")
	List<Connection> findByDeviceId(ObjectId deviceId);
}
