package com.wangsl.device.controller;

import com.wangsl.common.api.Result;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ProductBasicDTO;
import com.wangsl.device.model.dto.ProductCreateDTO;
import com.wangsl.device.model.dto.ProductEditDTO;
import com.wangsl.device.model.dto.ProductTypeOptionsDTO;
import com.wangsl.device.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * 创建产品
	 */
	@PostMapping
	public Result<Product> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        Product product = productService.createProduct(productCreateDTO);
		return Result.success(product);
	}

	/**
	 * 更新产品
	 */
	@PutMapping("/{productKey}")
	public Result<Product> updateProduct(@PathVariable String productKey,
	                                      @RequestBody ProductEditDTO productEditDTO) {
		Product product = productService.updateProduct(productKey, productEditDTO);
		return Result.success(product);
	}

	/**
	 * 获取产品详情
	 */
	@GetMapping("/{productKey}")
	public Result<Product> getProductDetail(@PathVariable String productKey) {
		Product product = productService.getProductDetail(productKey);
		return Result.success(product);
	}

	/**
	 * 获取产品详情（通过ID）
	 */
	@GetMapping("/id/{id}")
	public Result<Product> getProductDetailById(@PathVariable String id) {
		Product product = productService.getProductDetailById(id);
		return Result.success(product);
	}

	/**
	 * 获取产品列表（分页）
	 */
	@GetMapping("/list")
	public Result<Page<ProductBasicDTO>> getProductList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int pageSize,
		@RequestParam(defaultValue = "createdAt") String sort,
		@RequestParam(defaultValue = "desc") String direction) {

		// 创建分页请求
		Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortDirection, sort));

		// 获取产品列表
		Page<ProductBasicDTO> productPage = productService.getProductList(pageable);
		return Result.success(productPage);
	}

	/**
	 * 按名称搜索产品
	 */
	@GetMapping("/search")
	public Result<Page<ProductBasicDTO>> searchProductsByName(
		@RequestParam String name,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {

		// 创建分页请求
		Pageable pageable = PageRequest.of(page, size);

		// 搜索产品
		Page<ProductBasicDTO> productPage = productService.searchProductsByName(name, pageable);
		return Result.success(productPage);
	}

	/**
	 * 按产品类型过滤产品
	 */
	@GetMapping("/filter/type/{productType}")
	public Result<Page<ProductBasicDTO>> filterProductsByType(
		@PathVariable String productType,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {

		// 创建分页请求
		Pageable pageable = PageRequest.of(page, size);

		// 过滤产品
		Page<ProductBasicDTO> productPage = productService.filterProductsByType(productType, pageable);
		return Result.success(productPage);
	}

	/**
	 * 按状态过滤产品
	 */
	@GetMapping("/filter/status/{status}")
	public Result<Page<ProductBasicDTO>> filterProductsByStatus(
		@PathVariable String status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {

		// 创建分页请求
		Pageable pageable = PageRequest.of(page, size);

		// 过滤产品
		Page<ProductBasicDTO> productPage = productService.filterProductsByStatus(status, pageable);
		return Result.success(productPage);
	}

	/**
	 * 删除产品（通过产品Key）
	 */
	@DeleteMapping("/{productKey}")
	public Result<Void> deleteProduct(@PathVariable String productKey) {
		productService.deleteProduct(productKey);
		return Result.success();
	}

	/**
	 * 删除产品（通过ID）
	 */
	@DeleteMapping("/id/{id}")
	public Result<Void> deleteProductById(@PathVariable String id) {
		productService.deleteProductById(id);
		return Result.success();
	}

	/**
	 * 获取产品类型选项
	 */
	@GetMapping("/type-options")
	public Result<ProductTypeOptionsDTO> getProductTypeOptions() {
		ProductTypeOptionsDTO options = productService.getProductTypeOptions();
		return Result.success(options);
	}

}
