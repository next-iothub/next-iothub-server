package com.wangsl.device.service;

import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
	/**
	 * 创建产品
	 */
	Product createProduct(ProductCreateDTO productCreateDTO);

	/**
	 * 更新产品
	 */
	Product updateProduct(String productKey, ProductEditDTO productEditDTO);

	/**
	 * 获取产品详情
	 */
	Product getProductDetail(String productKey);

	/**
	 * 获取产品详情（通过ID）
	 */
	Product getProductDetailById(String id);

	/**
	 * 获取产品列表（分页）
	 */
	Page<ProductBasicDTO> getProductList(Pageable pageable);

	/**
	 * 按名称搜索产品（分页）
	 */
	Page<ProductBasicDTO> searchProductsByName(String name, Pageable pageable);

	/**
	 * 按产品类型过滤产品（分页）
	 */
	Page<ProductBasicDTO> filterProductsByType(String productType, Pageable pageable);

	/**
	 * 按状态过滤产品（分页）
	 */
	Page<ProductBasicDTO> filterProductsByStatus(String status, Pageable pageable);

	/**
	 * 删除产品（通过产品Key）
	 */
	void deleteProduct(String productKey);

	/**
	 * 删除产品（通过ID）
	 */
	void deleteProductById(String id);

	/**
	 * 获取产品类型选项
	 */
	ProductTypeOptionsDTO getProductTypeOptions();
}
