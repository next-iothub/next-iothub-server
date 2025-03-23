package com.wangsl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			// 允许指定的HTTP方法：GET, POST, PUT, DELETE, OPTIONS
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedOriginPatterns("*"); // 允许任何源发起的请求
	}

}
