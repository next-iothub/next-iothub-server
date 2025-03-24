package com.wangsl.device.model.dto;

import com.wangsl.device.model.Product;
import lombok.Data;

/**
 * 产品基本信息DTO（用于列表显示）
 */
@Data
public class ProductBasicDTO {
	private String id;
	private String productKey;
	private String productId;
	private String name;
	private String category;
	private String productType;
	private String status;
	private String nodeType;
	private Product.DeviceCount deviceCount;
	private String createdAt;
	private String updatedAt;
}
