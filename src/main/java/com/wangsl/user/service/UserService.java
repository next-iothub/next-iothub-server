package com.wangsl.user.service;

import com.wangsl.user.model.User;
import com.wangsl.user.repository.UserRepository;
import com.wangsl.user.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}


	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null)
			return user;
		throw new UsernameNotFoundException(username);
	}

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public User register(User user) {
		// todo: 数据合法校验
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new RuntimeException("用户名已存在");
		}
		user.setRoles(Collections.singletonList("USER")); // 默认角色
		user.setAuthorities(Collections.singletonList("BASIC")); // 默认权限
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	// public String login(User user) {
	// 	User existingUser = findByUsername(user.getUsername());
	// 	if (existingUser != null
	// 		&& passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
	// 		return jwtUtil.generateToken(existingUser.getUsername());
	// 	}
	// 	return null;
	// }
}
