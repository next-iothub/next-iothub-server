package com.wangsl.sys.controller;


import com.wangsl.common.api.PageUtil;
import com.wangsl.common.api.Result;
import com.wangsl.sys.model.SysRole;
import com.wangsl.common.api.CommonPage;
import com.wangsl.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;


    @GetMapping("/list")
    public Result<CommonPage<SysRole>> list(@RequestParam(defaultValue = "0") int current,
                                   @RequestParam(defaultValue = "10") int size) {
        // 分页查询
        Page<SysRole> roleList = sysRoleService.getRoleList(current, size);

        return Result.success(PageUtil.transform(roleList));
    }


    @GetMapping("/all")
    public Result<List<SysRole>> getAllRoles() {
        List<SysRole> roleList = sysRoleService.getAllRoles();
        return Result.success(roleList);
    }

    @PostMapping
    public Result addRole(@RequestBody SysRole role) {

        return Result.success();
    }
    //
    // @Operation(summary = "新增角色信息")
    // @PostMapping(value = "/addRole")
    // public Result<Boolean> add(@RequestBody SysRole sysRole) {
    //     return sysRoleService.add(sysRole);
    // }
    //
    // @Operation(summary = "查询角色资源ID")
    // @PostMapping(value = "/getRoleResourceId")
    // public Result<List<Long>> getRoleResourceId(@RequestBody Long roleId) {
    //     return sysRoleService.getRoleResourceId(roleId);
    // }
    //
    // @Operation(summary = "修改角色资源信息")
    // @PostMapping(value = "/updateRoleResourceInfo")
    // public Result<Boolean> updateRoleResourceInfo(@RequestBody RoleParam roleParam) {
    //     return sysRoleService.updateRoleResourceInfo(roleParam.getRoleId(), roleParam.getResourceId());
    // }

}
