package com.shangma.cn.service;

import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.Brand;
import com.shangma.cn.entity.Role;
import com.shangma.cn.service.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
public interface AdminService extends BaseService<Admin> {
    int addAdminAndRole(Admin entity);

    List<Role> getAdminRole(long id);

    int updateAdminAndRole(Admin entity);

    int deleteAdminRole(long adminId, long rid);

    Admin getAdminByAccount(String username);

    Map<String, Object> getAdminInfo();
}
