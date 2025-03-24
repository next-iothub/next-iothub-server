package com.wangsl.device.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 产品类型选项DTO
 */
@Data
public class ProductTypeOptionsDTO {
	private List<String> productTypes;
	private List<String> networkTypes;
	private List<String> protocolTypes;
	private List<String> authTypes;
	private List<String> dataFormats;
	private List<String> statusOptions;
	private List<String> nodeTypes;
}
