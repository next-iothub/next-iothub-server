package com.wangsl.user.security.config;

import com.wangsl.user.model.User;
import com.wangsl.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 通过用户名加载用户信息并封装
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException(username + "is not exists");
		}


		// 将角色和权限转换为 GrantedAuthority 对象
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(String role : user.getRoles()){
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		for(String authority : user.getAuthorities()){
			authorities.add(new SimpleGrantedAuthority(authority));
		}

		return new CustomUserDetails(user.getUsername(), user.getPassword(), authorities, user.getId());
	}
}
