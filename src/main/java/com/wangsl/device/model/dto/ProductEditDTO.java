package com.wangsl.device.model.dto;

import lombok.Data;

/**
 * 编辑表单
 */
@Data
public class ProductEditDTO {
	private String name;
	private String productType;
	private String networkType;
	private String authType;
	private String protocolType;
	private String description;
	private String dataFormat;
	private String nodeType;
}
