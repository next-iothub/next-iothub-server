package com.wangsl.auth.security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
	// 用户id
	private final String userId;

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String userId) {
		super(username, password, authorities);
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}
