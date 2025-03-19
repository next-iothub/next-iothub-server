package com.wangsl.user.model.vo;

import lombok.Data;

@Data
public class LoginVO {
	private String token;
	private String refreshToken;
}
