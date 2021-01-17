package com.shangma.cn.service;

import com.shangma.cn.entity.Brand;
import com.shangma.cn.entity.Category;
import com.shangma.cn.service.base.BaseService;

import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
public interface CategoryService extends BaseService<Category> {


    /**
     * 查询树型菜单
     */

    List<Category> getTreeData();
}
