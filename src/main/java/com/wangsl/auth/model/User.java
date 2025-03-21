package com.wangsl.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private ObjectId id;  // MongoDB _id
	private String username;
	private String password;
	// 角色（Role）：一般是用户的一种分类，表示用户所属于的一个大类。例如：ADMIN、USER、MANAGER 等。
	// 权限（Authority）：是更细粒度的权限，表示用户可以访问或操作某个特定的资源。例如：READ_PRIVILEGE、WRITE_PRIVILEGE 等。
	private List<String> roles; // 角色
	private List<String> authorities; // 权限
	private Date createTime;
}
