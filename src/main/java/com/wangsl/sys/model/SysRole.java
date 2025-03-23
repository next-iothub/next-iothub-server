package com.wangsl.sys.model;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "sys_role")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;
    private String roleName;
    private String roleCode;
    private Integer status; // 1 可用 2不可用
    private String roleDesc;
    private Integer type; // 0 普通用户 1 特殊用户
    private Date createTime;
}
