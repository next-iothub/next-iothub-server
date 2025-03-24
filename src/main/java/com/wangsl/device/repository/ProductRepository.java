package com.wangsl.device.repository;

import com.wangsl.device.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {

	// 根据产品名查询
	Product findByName(String name);

	// 通过
	Optional<Product> findByUserIdAndName(String userId, String name);

	Optional<Product> findByUserIdAndProductKey(ObjectId userId, String productKey);

	Optional<Product> findByProductKey(String productKey);

	// 根据用户 ID 和产品 Key 删除产品
	long deleteByUserIdAndProductKey(ObjectId userId, String productKey);

	// 根据用户ID进行分页查询
	Page<Product> findByUserId(ObjectId userId, Pageable pageable);

	/**
	 * 根据productName模糊查询
	 */
	Page<Product> findByNameContaining(String name, Pageable pageable);

	/**
	 * 根据用户ID查询所有产品（分页）
	 */
	Page<Product> findByUserId(String userId, Pageable pageable);

	/**
	 * 根据用户ID和产品Key查询产品
	 */
	Optional<Product> findByUserIdAndProductKey(String userId, String productKey);

	/**
	 * 检查产品Key是否已存在
	 */
	boolean existsByProductKey(String productKey);

	/**
	 * 根据用户ID和产品ID查询产品
	 */
	Optional<Product> findByUserIdAndId(String userId, String id);

	/**
	 * 根据用户ID和产品名称模糊查询产品（分页）
	 */
	@Query("{'userId': ?0, 'name': {$regex: ?1, $options: 'i'}}")
	Page<Product> findByUserIdAndNameLike(String userId, String name, Pageable pageable);

	/**
	 * 根据用户ID和产品类型查询产品（分页）
	 */
	Page<Product> findByUserIdAndProductType(String userId, String productType, Pageable pageable);

	/**
	 * 根据用户ID和产品状态查询产品（分页）
	 */
	Page<Product> findByUserIdAndStatus(String userId, String status, Pageable pageable);

	/**
	 * 删除指定用户的产品（通过产品Key）
	 */
	void deleteByUserIdAndProductKey(String userId, String productKey);

	/**
	 * 删除指定用户的产品（通过ID）
	 */
	void deleteByUserIdAndId(String userId, String id);

}
