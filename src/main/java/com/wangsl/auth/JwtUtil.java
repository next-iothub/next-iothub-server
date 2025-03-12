package com.wangsl.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	@Value("${emqx.auth.secret}")
	private String SECRET_KEY; // 256-bit 密钥

	private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000; // 2小时

	// 生成 JWT 令牌
	public String generateToken(String username) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

		return Jwts.builder()
			.setSubject(username) // 设备 ID
			.claim("role", "device") // 角色信息（可选）
			.setIssuedAt(new Date()) // 签发时间
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
			.signWith(key, SignatureAlgorithm.HS256) // 签名算法
			.compact();
	}

	// 解析 JWT
	public Claims parseToken(String token) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
