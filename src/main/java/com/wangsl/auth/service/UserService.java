package com.wangsl.auth.service;

import com.wangsl.common.exception.IothubExceptionEnum;
import com.wangsl.common.exception.ExceptionUtil;
import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.auth.model.RegisterParam;
import com.wangsl.auth.model.User;
import com.wangsl.auth.model.vo.UserInfoVO;
import com.wangsl.auth.repository.UserRepository;
import com.wangsl.common.utils.JwtUtil;
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
	 * find user by username
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
	 * registered user
	 * @param registerParam
	 * @return
	 */
	public User register(RegisterParam registerParam) {
		// todo: 数据合法校验
		if (userRepository.existsByUsername(registerParam.getUserName())) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_USERNAME_EXIST);
		}
		User user = new User();
		user.setUsername(registerParam.getUserName());
		user.setPassword(passwordEncoder.encode(registerParam.getPassword()));
		user.setRoles(Collections.singletonList("USER")); // 默认角色
		user.setAuthorities(Collections.singletonList("BASIC")); // 默认权限
		return userRepository.save(user);
	}

	/**
	 * get basic user information
	 * @return
	 */
	public UserInfoVO getUserInfo() {
		UserInfoVO userInfoVO = UserInfoVO.builder()
			.userId(SecurityContextUtil.getCurrentUserId().toString())
			.userName(SecurityContextUtil.getCurrentUsername())
			.roles(SecurityContextUtil.getAuthoritiesAndRole())
			.build();
		return userInfoVO;
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
