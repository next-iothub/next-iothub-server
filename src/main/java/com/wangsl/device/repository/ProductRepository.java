package com.wangsl.device.repository;

import com.wangsl.device.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {

	Product findByProductName(String productName);

	Optional<Product> findByUserIdAndProductName(ObjectId userId, String productName);

	Optional<Product> findByUserIdAndProductKey(ObjectId userId, String productKey);

	Optional<Product> findByProductKey(String productKey);

	// 根据用户 ID 和产品 ID 删除产品
	long deleteByUserIdAndId(ObjectId userId, ObjectId productId);

	// 根据用户ID进行分页查询
	Page<Product> findByUserId(ObjectId userId, Pageable pageable);
}
