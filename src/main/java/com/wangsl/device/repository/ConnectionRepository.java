package com.wangsl.device.repository;

import com.wangsl.device.model.Connection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends MongoRepository<Connection, ObjectId>{

}
