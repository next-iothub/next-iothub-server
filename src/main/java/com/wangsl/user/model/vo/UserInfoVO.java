package com.wangsl.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserInfoVO {
	private String userId;
	private String userName;
	private List<String> roles;
	private List<String> buttons;
}
