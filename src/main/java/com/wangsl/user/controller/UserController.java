package com.wangsl.user.controller;

import com.wangsl.common.web.model.Result;
import com.wangsl.user.model.vo.UserInfoVO;
import com.wangsl.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/info")
	public Result<UserInfoVO> getUserInfo(){

		UserInfoVO userInfoVO = userService.getUserInfo();
		return Result.success(userInfoVO);
	}
}
