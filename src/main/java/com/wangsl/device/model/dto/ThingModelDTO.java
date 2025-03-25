package com.wangsl.device.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ThingModelDTO {
	// 属性 DTO
	@Data
	public static class PropertyDTO {
		@NotBlank(message = "属性ID不能为空")
		@Size(max = 50, message = "属性ID长度不能超过50")
		private String id;

		@NotBlank(message = "属性名称不能为空")
		private String name;

		@NotBlank(message = "数据类型不能为空")
		private String dataType;

		private String unit;
		private Double min;
		private Double max;
		private Double step;

		@Pattern(regexp = "r|w|rw", message = "模式只能是r、w或rw")
		private String mode;

		private Boolean required;
		private Map<String, String> specs;
	}

	// 事件 DTO
	@Data
	public static class EventDTO {
		@NotBlank(message = "事件ID不能为空")
		private String id;

		@NotBlank(message = "事件名称不能为空")
		private String name;

		@Pattern(regexp = "info|alert|error", message = "事件类型不正确")
		private String type;

		private List<ParameterDTO> params;
	}

	// 服务 DTO
	@Data
	public static class ServiceDTO {
		@NotBlank(message = "服务ID不能为空")
		private String id;

		@NotBlank(message = "服务名称不能为空")
		private String name;

		private List<ParameterDTO> input;
		private List<ParameterDTO> output;
	}

	// 参数 DTO
	@Data
	public static class ParameterDTO {
		@NotBlank(message = "参数ID不能为空")
		private String id;

		@NotBlank(message = "参数名称不能为空")
		private String name;

		@NotBlank(message = "数据类型不能为空")
		private String dataType;

		private Map<String, String> specs;
	}
}
