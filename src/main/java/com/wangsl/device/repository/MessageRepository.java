package com.wangsl.device.repository;

import com.wangsl.device.model.Device;
import com.wangsl.device.model.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {


}
