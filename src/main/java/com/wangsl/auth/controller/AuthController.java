package com.wangsl.auth.controller;

import com.wangsl.auth.model.LoginParam;
import com.wangsl.auth.model.RegisterParam;
import com.wangsl.auth.model.User;
import com.wangsl.auth.model.vo.LoginVO;
import com.wangsl.auth.repository.UserRepository;
import com.wangsl.auth.security.config.CustomUserDetails;
import com.wangsl.auth.service.UserService;
import com.wangsl.common.api.Result;
import com.wangsl.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public Result<LoginVO> login(@RequestBody LoginParam loginParam) {
		// 认证
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginParam.getUserName(), loginParam.getPassword())
		);

		// 认证通过将 authentication 保存在 security context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		String jwtToken = jwtUtil.generateToken(customUserDetails);

		LoginVO loginVO = new LoginVO();
		loginVO.setToken(jwtToken);
		loginVO.setRefreshToken(jwtToken);
		return Result.success(loginVO);
	}


	/**
	 * 用户注册
	 * @param registerParam
	 * @return
	 */
	@PostMapping("/register")
	public Result<User> register(@RequestBody RegisterParam registerParam) {
		User resUser = userService.register(registerParam);
		// return Result.success(resUser);
		return Result.success(resUser);
	}
}
