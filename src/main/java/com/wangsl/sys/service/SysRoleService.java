package com.wangsl.sys.service;

import com.wangsl.sys.model.SysRole;
import com.wangsl.sys.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleService {

	private final SysRoleRepository roleRepository;

	@Autowired
	public SysRoleService(SysRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<SysRole> getAllRoles() {
		return roleRepository.findAll();
	}

	/**
	 * 分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<SysRole> getRoleList(int page, int pageSize) {
		// 分页参数
		Pageable pageable = PageRequest
			.of(--page, pageSize, Sort.by(Sort.Order.asc("createTime"))); // 按创建时间升序排序
		return roleRepository.findAll(pageable);
	}

	public void addRole(SysRole role) {
		roleRepository.save(role);
	}

	// public List<SysRole> getActiveRoles() {
	// 	return roleRepository.findActiveRoles();
	// }
	//
	// public Optional<SysRole> getRoleById(String id) {
	// 	return roleRepository.findById(id);
	// }
	//
	// public SysRole createRole(SysRole role) {
	// 	role.setCreateTime(LocalDateTime.now());
	// 	role.setIsDeleted(0);
	// 	return roleRepository.save(role);
	// }
	//
	// public Optional<SysRole> updateRole(String id, SysRole role) {
	// 	return roleRepository.findById(id)
	// 		.map(existingRole -> {
	// 			role.setId(id);
	// 			role.setUpdateTime(LocalDateTime.now());
	// 			return roleRepository.save(role);
	// 		});
	// }
	//
	// public boolean deleteRole(String id) {
	// 	return roleRepository.findById(id)
	// 		.map(role -> {
	// 			role.setIsDeleted(1);
	// 			role.setDeleteTime(LocalDateTime.now());
	// 			roleRepository.save(role);
	// 			return true;
	// 		}).orElse(false);
	// }
	//
	// public List<SysResource> getRoleResources(String roleId) {
	// 	return roleRepository.findById(roleId)
	// 		.map(role -> resourceRepository.findAllById(role.getResourceIds()))
	// 		.orElse(List.of());
	// }
	//
	// public boolean assignResourcesToRole(String roleId, List<String> resourceIds) {
	// 	return roleRepository.findById(roleId)
	// 		.map(role -> {
	// 			role.setResourceIds(resourceIds);
	// 			role.setUpdateTime(LocalDateTime.now());
	// 			roleRepository.save(role);
	// 			return true;
	// 		}).orElse(false);
	// }
}
