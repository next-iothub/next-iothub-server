package com.wangsl.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysRoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String roleName;
    private String roleCode;
    private String roleDesc;
}
