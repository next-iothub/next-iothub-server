package com.wangsl.user.security.jwt;

import com.wangsl.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * jjwt 0.12.3 新版使用
 */
@Component
public class JwtUtil {

	private final String SECRET = "iotplatformuserauthsecretsecretsecret";

	// 秘钥实例
	public SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

	// 过期时间（单位：秒）
	public final long EXPIRATION_TIME = 24 * 60 * 60;

	//加密算法
	private final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

	// jwt签发者
	private final String JWT_ISS = "Austin Slwang";

	// jwt 主题
	private final String SUBJECT = "Peripherals";


	/**
	 * 生成token
	 */
	public String generateToken(UserDetails userDetails) {
		// 令牌id
		String uuid = UUID.randomUUID().toString();
		// 过期时间
		Date exprireDate = Date.from(Instant.now().plusSeconds(EXPIRATION_TIME));
		return Jwts.builder()
			.header() // 头部信息
			.add("typ", "JWT")
			.add("alg", "HS256")
			.and()
			.claim("username", userDetails.getUsername()) // payload 负载信息
			.claim("roles", userDetails.getAuthorities()) // payload 负载信息
			.id(uuid) // 令牌ID
			.subject(SUBJECT) // 主题
			.issuer(JWT_ISS) // 签发者
			.issuedAt(new Date()) // 签发时间
			.expiration(exprireDate) // 过期时间
			.signWith(KEY, ALGORITHM) // 签名
			.compact();
	}

	/**
	 * 解析token
	 *
	 * @param token
	 */
	public Jws<Claims> parseToken(String token) {
		return Jwts.parser()
			.verifyWith(KEY)
			.build()
			.parseSignedClaims(token);
	}

	/**
	 * 解析 payload
	 * @param token
	 * @return
	 */
	public Claims parsePayload(String token) {
		return parseToken(token).getPayload();
	}

	/**
	 * 从payload中解析username
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token){
		return parsePayload(token).get("username", String.class);
	}


	/**
	 * token是否有效
	 * @param token
	 * @return
	 */
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
				.verifyWith(KEY)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
