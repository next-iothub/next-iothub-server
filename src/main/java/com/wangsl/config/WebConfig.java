package com.wangsl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 允许 http://localhost:8081 来源的请求访问所有接口
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8081");
	}
}
