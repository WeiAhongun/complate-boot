package com.shangma.cn.service.impl;

import com.shangma.cn.common.cache.TokenService;
import com.shangma.cn.common.utils.TreeUtils;
import com.shangma.cn.entity.*;
import com.shangma.cn.mapper.*;
import com.shangma.cn.service.AdminService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public int addAdminAndRole(Admin entity) {
        addEntity(entity);
        List<Long> ids = entity.getRoleIds();
        ids.forEach(i->{
            adminRoleMapper.insert(new AdminRoleKey(entity.getId(),i));
        });
        return 1;
    }

    @Override
    public List<Role> getAdminRole(long id) {
        AdminRoleExample example = new AdminRoleExample();
        example.createCriteria().andAdminIdEqualTo(id);
        List<AdminRoleKey> adminRoleKeys = adminRoleMapper.selectByExample(example);
        List<Role> list = new ArrayList<>();
        adminRoleKeys.forEach(i->{
            list.add(roleMapper.selectByPrimaryKey(i.getRoleId()));
        });
        return list;
    }

    @Override
    public int updateAdminAndRole(Admin entity) {
        updateEntity(entity);
        List<Long> ids = entity.getRoleIds();
        ids.forEach(i->{
            adminRoleMapper.insert(new AdminRoleKey(entity.getId(),i));
        });
        return 1;
    }

    @Override
    public int deleteAdminRole(long adminId, long rid) {
        AdminRoleExample example = new AdminRoleExample();
        example.createCriteria().andAdminIdEqualTo(adminId).andRoleIdEqualTo(rid);
        adminRoleMapper.deleteByExample(example);
        return 1;
    }

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin getAdminByAccount(String username) {
        AdminExample example = new AdminExample();
        example.createCriteria().andAdminAccountEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(admins)){
            return admins.get(0);
        }
        return null;
    }

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public Map<String, Object> getAdminInfo() {
        LoginAdmin loginAdmin = tokenService.getLoginAdmin();
        Map<String, Object> map = new HashMap<>();
        map.put("admin",loginAdmin.getAdmin());
        List<Role> adminRole = getAdminRole(loginAdmin.getAdmin().getId());
        Set<Menu> menus = new HashSet<>();
        adminRole.forEach(i->{
            RoleMenuExample example = new RoleMenuExample();
            example.createCriteria().andRoleIdEqualTo(i.getId());
            List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(example);
            roleMenuKeys.forEach(i1->{
                Long menuId = i1.getMenuId();
                Menu menu = menuMapper.selectByPrimaryKey(menuId);
                if(!menu.getMenuType().equalsIgnoreCase("B")){
                    menus.add(menu);
                }
            });
        });
        List<Menu> rootList = menus.stream().filter(item -> item.getParentId().equals(0L)).collect(Collectors.toList());
        List<Menu> menus1 = TreeUtils.buildTreeData(new ArrayList<>(menus), rootList);
        map.put("menus",menus1);
        return map;
    }
}
