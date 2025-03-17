package com.wangsl.user.controller;

import com.wangsl.common.exception.ex.IothubException;
import com.wangsl.common.exception.ex.UnauthorizedException;
import com.wangsl.common.web.model.Result;
import com.wangsl.user.model.LoginParam;
import com.wangsl.user.model.User;
import com.wangsl.user.repository.UserRepository;
import com.wangsl.user.security.config.CustomUserDetails;
import com.wangsl.user.security.jwt.JwtUtil;
import com.wangsl.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Autowired
	public AuthController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * 用户登录
	 * @param loginParam
	 * @return
	 */
	@PostMapping("/login")
	public Result<String> login(@RequestBody LoginParam loginParam) {
		// 认证
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword())
		);

		// 认证通过将 authentication 保存在 security context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		String jwtToken = jwtUtil.generateToken(customUserDetails);
		return Result.success(jwtToken);
	}


	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		User resUser = userService.register(user);
		// return Result.success(resUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(resUser);
	}
}
