package com.wangsl.user.repository;

import com.wangsl.user.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

	User findByUsername(String username);

	boolean existsByUsername(String username);
}
