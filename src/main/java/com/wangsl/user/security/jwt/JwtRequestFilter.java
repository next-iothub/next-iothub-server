package com.wangsl.user.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Autowired
	public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String token = request.getHeader("Authorization");
		String username = null;

		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7);

			// todo：颁发token时存入redis，这里从redis中获取

			// 解析token获取payload中的username
			username = jwtUtil.getUsernameFromToken(jwtToken);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// 当使用token时，从token解析username去查询用户信息
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (username.equals(userDetails.getUsername())) {
				// 创建一个身份认证对象
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				// 设置身份认证信息到 SecurityContext 中，这样后续的代码可以直接使用这个用户信息
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}
