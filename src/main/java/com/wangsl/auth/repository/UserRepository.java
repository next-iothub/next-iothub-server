package com.wangsl.auth.repository;

import com.wangsl.auth.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

	User findByUserName(String userName);

	boolean existsByUserName(String userName);

}
