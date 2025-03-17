package com.wangsl.device.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Id
	private ObjectId id;  // MongoDB 自动生成的唯一ID
	private ObjectId userId; // 产品所属用户id
	private String productName; // 产品名称
	private String productKey; // 产品key
	private String description; // 产品描述
	private Date createTime; // 创建时间
}
