package com.wangsl.auth.controller;

import com.wangsl.auth.model.User;
import com.wangsl.auth.model.vo.UserInfoVO;
import com.wangsl.auth.service.UserService;
import com.wangsl.common.api.CommonPage;
import com.wangsl.common.api.PageUtil;
import com.wangsl.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/add")
	public Result addUser(@RequestBody User user){
		userService.addUser(user);
		return Result.success();
	}

	@GetMapping("/list")
	public Result<CommonPage<User>> list(@RequestParam(defaultValue = "0") int current,
	                                        @RequestParam(defaultValue = "10") int size) {
		// 分页查询
		Page<User> userList = userService.getUserList(current, size);
		return Result.success(PageUtil.transform(userList));
	}

}
