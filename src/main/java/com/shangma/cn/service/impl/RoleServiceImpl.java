package com.shangma.cn.service.impl;

import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.Role;
import com.shangma.cn.entity.RoleMenuExample;
import com.shangma.cn.entity.RoleMenuKey;
import com.shangma.cn.mapper.MenuMapper;
import com.shangma.cn.mapper.RoleMenuMapper;
import com.shangma.cn.service.RoleService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public int addRoleAndMenu(Role entity) {
        addEntity(entity);
        List<Long> menuIds = entity.getMenuIds();
        menuIds.forEach(i->{
            roleMenuMapper.insert(new RoleMenuKey(entity.getId(),i));
        });
        return 1;
    }

    @Override
    public Role findRoleById(Long id) {
        Role role = findById(id);
        RoleMenuExample example = new RoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(id);
        List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(example);
        List<Long> menuIds = new ArrayList<>();
        //判断有没有孩子，没有孩子就放入list中
        List<Menu> menus = new ArrayList<>();
        roleMenuKeys.forEach(i->{
            menus.add(menuMapper.selectByPrimaryKey(i.getMenuId()));
        });
        menus.forEach(i->{
            if(!hasChildren(menus,i)){
                menuIds.add(i.getId());
            }
        });
        role.setMenuIds(menuIds);
        return role;
    }

    @Override
    public int updateRoleAndMenu(Role entity) {
        updateEntity(entity);
        List<Long> menuIds = entity.getMenuIds();
        RoleMenuExample example = new RoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(entity.getId());
        roleMenuMapper.deleteByExample(example);
        menuIds.forEach(i->{
            roleMenuMapper.insert(new RoleMenuKey(entity.getId(),i));
        });
        return 1;
    }

    @Override
    public int deleteRoleAndMenuById(Long id) {
        deleteById(id);
        RoleMenuExample roleMenuExample = new RoleMenuExample();
        roleMenuExample.createCriteria().andRoleIdEqualTo(id);
        roleMenuMapper.deleteByExample(roleMenuExample);
        return 1;
    }

    public boolean hasChildren(List<Menu> menuList,Menu fatherMenu){
        return menuList.stream().anyMatch(item -> item.getParentId().equals(fatherMenu.getId()));
    }
}
