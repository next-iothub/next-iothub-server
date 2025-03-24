package com.wangsl.device.model.dto;

import com.wangsl.device.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 产品创建请求DTO
 */
@Data
@Builder
public class ProductCreateDTO {
	private String name;
	private String description;
	private String category;
	private List<String> categoryPath;
	private String model;
	private String manufacturer;

	private String productType;
	private String networkType;
	private String protocolType;
	private String authType;
	private String dataFormat;
	private String status;
	private List<String> tags;
	private String nodeType;
	private Boolean dataEncryption;

	private Product.Features features;
	private Product.ThingModel thingModel;
	private Product.Configurations configurations;
}
