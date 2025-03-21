package com.wangsl;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@EnableKnife4j
@SpringBootApplication
public class IothubServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IothubServerApplication.class, args);
	}

}
