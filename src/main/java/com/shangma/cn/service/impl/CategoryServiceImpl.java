package com.shangma.cn.service.impl;

import com.shangma.cn.entity.Brand;
import com.shangma.cn.entity.Category;
import com.shangma.cn.mapper.CategoryMapper;
import com.shangma.cn.service.BrandService;
import com.shangma.cn.service.CategoryService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:37
 * 文件说明：
 */
@Service
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getTreeData() {
        List<Category> categories = categoryMapper.selectByExample(null);
        List<Category> root = categories.stream().filter(item -> item.getParentId().equals(0L)).collect(Collectors.toList());

        root.forEach(item -> {
            getChildren(categories, item);
        });
        return root;

    }


    public void getChildren(List<Category> list, Category category) {
        List<Category> children = list.stream().filter(item -> item.getParentId().equals(category.getId())).collect(Collectors.toList());
        if (children.size() > 0) {
            category.setChildren(children);
            children.forEach(item -> {
                getChildren(list, item);
            });
        }
    }
}
