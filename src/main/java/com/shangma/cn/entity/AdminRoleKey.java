package com.shangma.cn.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AdminRoleKey {
    private Long adminId;

    private Long roleId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
