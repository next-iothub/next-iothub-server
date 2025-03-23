package com.wangsl.auth.model;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "user")
@Builder
public class User {


	@Id
	private ObjectId id;  // MongoDB _id
	private String userName; // 用户名
	private String nickName; // 昵称
	private String password; // 密码
	private String userGender; // 性别
	private String userPhone; // 手机号
	private String userEmail; // 邮箱
	private Integer status; // 用户状态 1 正常  2 禁用
	private LocalDateTime lastLoginTime; // 登录时间
	private String lastLoginIp; // 登录ip
	private List<String> roles; // 角色
	private List<String> authorities; // 权限
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Date deleteTime; // 删除时间
	private String createBy; // 被谁创建
	private String updateBy; // 被谁更新
	private Integer isDeleted; // 是否删除
}
