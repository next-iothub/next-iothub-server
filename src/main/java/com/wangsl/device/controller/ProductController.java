package com.wangsl.device.controller;

import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.common.web.model.Result;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ProductDTO;
import com.wangsl.device.model.vo.ProductVO;
import com.wangsl.device.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	 * 根据产品名创建产品信息
	 * @param productDTO
	 * @return
	 */
	@PostMapping
	public Result<ProductVO> createProduct(@RequestBody ProductDTO productDTO) {
		// 获取当前用户id
		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		ProductVO productVO = productService.createProduct(productDTO, currentUserId);
		return Result.success(productVO);
	}

	/**
	 * 通过productKey获取产品信息
	 * @param productKey
	 * @return
	 */
	@GetMapping("/{productKey}")
	public Result<Product> getProductByProductKey(@PathVariable String productKey) {
		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		Product product = productService.getProductByProductKey(productKey, currentUserId);
		return product != null ? Result.success(product) : Result.fail("未找到");
	}

	/**
	 * 分页查询
	 * @param page 页码
	 * @param size 每页数量
	 * @return
	 */
	@GetMapping("/user/list")
	public Result<Page<Product>> getProductsByUserId(
		@RequestParam int page,
		@RequestParam int size) {
		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		Page<Product> productPage = productService.getProductsByUserId(currentUserId, page, size);
		return Result.success(productPage);
	}

	/**
	 * 根据产品 ID 和用户 ID 删除产品
	 *
	 * @param productId
	 * @return
	 */
	@DeleteMapping("/{productId}")
	public Result<Object> deleteProduct(
		@PathVariable ObjectId productId) {

		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		boolean isDeleted = productService.deleteProduct(currentUserId, productId);
		if (isDeleted) {
			return Result.success("Product deleted successfully");
		} else {
			return Result.fail().code(404).msg("Product not found or not owned by the user");
		}
	}


}
