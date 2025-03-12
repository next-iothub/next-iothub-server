package com.wangsl.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备连接EMQX认证token生成接口
 */
@RestController
@RequestMapping("/auth")
public class Authentication {

	private final JwtUtil jwtUtil;

	@Autowired
	public Authentication(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
	 * 生成设备认证的jwt
	 * @return
	 */
	@PostMapping("/token")
	public Map<String, String> token() {
		String token = jwtUtil.generateToken("test");
		Map<String, String> res = new HashMap<>();
		res.put("token", token);
		return res;
	}
}
