package com.wangsl.sys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

/**
 * 系统数据字典实体类
 * 对应MongoDB中的dictionaries集合
 */
@Data
@Document(collection = "dictionaries")
@CompoundIndexes({
	@CompoundIndex(name = "type_code_idx", def = "{'type': 1, 'code': 1}", unique = true)
})
@AllArgsConstructor
public class Dictionary {
	@Id
	private String id;

	// 字典类型，如product_type, network_type等
	private String type;

	// 字典代码，如device, gateway, wifi等
	private String code;

	// 字典显示值
	private String value;

	// 排序值
	private Integer sort;

	// 描述
	private String description;

	// 是否启用
	private Boolean enabled;

	// 父级ID，用于层级字典
	private String parentId;

	// 附加属性，如图标、颜色等
	private Map<String, Object> attributes;

	private Date createdAt;
	private Date updatedAt;
}
