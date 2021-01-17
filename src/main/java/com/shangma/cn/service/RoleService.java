package com.shangma.cn.service;

import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.Role;
import com.shangma.cn.service.base.BaseService;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
public interface RoleService extends BaseService<Role> {
    int addRoleAndMenu(Role entity);

    Role findRoleById(Long id);

    int updateRoleAndMenu(Role entity);

    int deleteRoleAndMenuById(Long id);
}
