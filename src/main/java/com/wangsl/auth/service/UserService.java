package com.wangsl.auth.service;

import com.wangsl.auth.model.RegisterParam;
import com.wangsl.auth.model.User;
import com.wangsl.auth.model.vo.UserInfoVO;
import com.wangsl.auth.repository.UserRepository;
import com.wangsl.common.exception.ExceptionUtil;
import com.wangsl.common.exception.IothubExceptionEnum;
import com.wangsl.common.utils.JwtUtil;
import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.sys.enums.SysRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}


	/**
	 * find user by username
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		if (user != null)
			return user;
		throw new UsernameNotFoundException(userName);
	}

	/**
	 * registered user
	 * @param registerParam
	 * @return
	 */
	public User register(RegisterParam registerParam) {
		// todo: 数据合法校验
		if (userRepository.existsByUserName(registerParam.getUserName())) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_USERNAME_EXIST);
		}
		// User user = new User();
		// user.setUsername(registerParam.getUserName());
		// user.setPassword());
		// user.setRoles(Collections.singletonList("USER")); // 默认角色
		// user.setAuthorities(Collections.singletonList("BASIC")); // 默认权限
		User user = User.builder()
			.userName(registerParam.getUserName())
			.nickName(registerParam.getUserName())
			.password(passwordEncoder.encode(registerParam.getPassword()))
			.userGender("2")
			.userPhone("11111111111")
			.userEmail("xxxxx.doe@example.com")
			.status(1)
			.lastLoginTime(LocalDateTime.now())
			.lastLoginIp("0.0.0.0")
			.roles(List.of("USER"))
			.authorities(List.of("READ"))
			.createTime(new Date())
			.updateTime(new Date())
			.createBy(SysRoleEnum.ADMIN.getRoleName())
			.isDeleted(0)
			.build();
		return userRepository.save(user);
	}

	/**
	 * get basic user information
	 * @return
	 */
	public UserInfoVO getUserInfo() {
		UserInfoVO userInfoVO = UserInfoVO.builder()
			.userId(SecurityContextUtil.getCurrentUserId().toString())
			.userName(SecurityContextUtil.getCurrentUsername())
			.roles(SecurityContextUtil.getAuthoritiesAndRole())
			.build();
		return userInfoVO;
	}

	public void addUser(User user) {
		if (userRepository.existsByUserName(user.getUserName())) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_USERNAME_EXIST);
		}

		User user1 = User.builder()
			.userName("johnDoe")
			.nickName("John")
			.password(passwordEncoder.encode("johnDoe")) // 默认密码是用户名
			.userGender("Male")
			.userPhone("1234567890")
			.userEmail("john.doe@example.com")
			.status(1)
			.lastLoginTime(LocalDateTime.now())
			.lastLoginIp("192.168.1.1")
			.roles(Collections.singletonList(SysRoleEnum.USER.getRoleCode()))
			.authorities(List.of("READ", "WRITE", "DELETE"))
			.createTime(new Date())
			.updateTime(new Date())
			.createBy(SysRoleEnum.ADMIN.getRoleName())
			.isDeleted(0)
			.build();
		userRepository.save(user1);
	}

	/**
	 * 分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<User> getUserList(int page, int pageSize) {
		// 分页参数
		Pageable pageable = PageRequest
			.of(--page, pageSize, Sort.by(Sort.Order.asc("createTime"))); // 按创建时间升序排序
		return userRepository.findAll(pageable);
	}
}
