package com.shangma.cn.service.impl;

import com.shangma.cn.common.utils.TreeUtils;
import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.MenuExample;
import com.shangma.cn.entity.Role;
import com.shangma.cn.mapper.MenuMapper;
import com.shangma.cn.service.MenuService;
import com.shangma.cn.service.RoleService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public PageVo<Menu> getTreeData() {
        MenuExample example = new MenuExample();
        example.createCriteria().andParentIdEqualTo(0L);
        List<Menu> rooList = menuMapper.selectByExample(example);
        List<Menu> allList = menuMapper.selectByExample(null);
        TreeUtils.buildTreeData(allList,rooList);
        PageVo<Menu> menuPageVo = setPage(rooList);
        return menuPageVo;
    }
}
