package com.shangma.cn.entity;

import com.shangma.cn.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class Role extends BaseEntity {
    private String roleName;
    private String roleDesc;
    private List<Long> menuIds;
}
