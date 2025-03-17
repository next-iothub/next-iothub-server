package com.wangsl.user.security.config;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
	// 用户id
	private final ObjectId id;

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, ObjectId id) {
		super(username, password, authorities);
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}
}
