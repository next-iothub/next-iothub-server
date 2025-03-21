package com.wangsl.common.utils;

import com.wangsl.auth.security.config.CustomUserDetails;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityContextUtil {

	/**
	 * 获取当前用户的 Authentication 对象
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取当前用户的用户名
	 */
	public static String getCurrentUsername() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((CustomUserDetails) principal).getUsername();
			}
		}
		return null;
	}

	/**
	 * 获取当前用户的 ObjectId
	 */
	public static ObjectId getCurrentUserId() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((CustomUserDetails) principal).getId();
			}
		}
		return null;
	}


	public static List<String> getAuthoritiesAndRole(){
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Collection<? extends GrantedAuthority> authorities =
				authentication.getAuthorities();
			List<String> authoritiesString = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
			return authoritiesString;
		}
		return null;
	}

}
