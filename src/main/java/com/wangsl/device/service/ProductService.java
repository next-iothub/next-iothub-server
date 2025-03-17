package com.wangsl.device.service;

import com.wangsl.common.exception.ex.IothubException;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ProductDTO;
import com.wangsl.device.model.vo.ProductVO;
import com.wangsl.device.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * 创建产品
	 *
	 * @param productDTO
	 * @param currentUserId
	 * @return
	 */
	public ProductVO createProduct(ProductDTO productDTO, ObjectId currentUserId) {
		// 查重
		if (productRepository.findByUserIdAndProductName(currentUserId, productDTO.getProductName()).isPresent()) {
			IothubException.cast("Product name already exists for this user");
		}

		Product product = Product.builder()
			.userId(currentUserId)  // 填写用户 ID
			.productName(productDTO.getProductName())  // 填写产品名称
			.productKey(generateProductKey())  // 填写产品 Key
			.description(productDTO.getDescription())  // 填写描述
			.createTime(new Date())  // 使用当前时间作为创建时间
			.build();
		productRepository.save(product);
		ProductVO productVO = new ProductVO();
		BeanUtils.copyProperties(product, productVO);
		return productVO;
	}

	/**
	 * 通过productKey查找产品
	 * @param productKey
	 * @param currentUserId
	 * @return
	 */
	public Product getProductByProductKey(String productKey, ObjectId currentUserId) {
		Product product = productRepository.findByProductKey(productKey)
			.filter(p -> p.getUserId().equals(currentUserId))
			.orElse(null);

		if (product == null) {
			IothubException.cast("不存在");
		}

		return product;

	}

	/**
	 * 根据用户名分页查询产品列表
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Product> getProductsByUserId(ObjectId userId, int page, int size) {
		// 分页参数
		Pageable pageable = PageRequest
			.of(page, size, Sort.by(Sort.Order.asc("createTime"))); // 按创建时间升序排序
		return productRepository.findByUserId(userId, pageable);
	}

	/**
	 * 根据产品 ID 和用户 ID 删除产品
	 * @param userId
	 * @param productId
	 * @return
	 */
	public boolean deleteProduct(ObjectId userId, ObjectId productId) {
		// 确认产品是否存在，并且属于指定用户
		long deletedCount = productRepository.deleteByUserIdAndId(userId, productId);
		return deletedCount > 0; // 如果删除了记录，返回 true
	}


	// 生成productKey
	private String generateProductKey() {
		return getRandomString(14);
	}

	// 生成指定长度的随机字符串
	private String getRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder();
		for (int i = 0; i < length; i++) {
			randomString.append(characters.charAt(random.nextInt(characters.length())));
		}
		return randomString.toString();
	}
}
