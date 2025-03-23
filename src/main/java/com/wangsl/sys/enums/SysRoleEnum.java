package com.wangsl.sys.enums;

public enum SysRoleEnum {
	ADMIN("管理员", "ADMIN", "可以管理用户"),
	SUPER("超级管理员", "SUPER", "所有功能（管理角色）"),
	USER("普通用户", "USER", "普通用户操作"),
	GUEST("游客", "GUEST", "游客预览");

	private final String roleName;
	private final String roleCode;
	private final String roleDesc;

	SysRoleEnum(String roleName, String roleCode, String roleDesc) {
		this.roleName = roleName;
		this.roleCode = roleCode;
		this.roleDesc = roleDesc;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}
}
