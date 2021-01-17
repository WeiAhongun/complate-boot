package com.shangma.cn.controller;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Menu;
import com.shangma.cn.service.MenuService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:48
 * 文件说明：
 */
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {


    @Autowired
    private MenuService menuService;

    @GetMapping
    public AxiosResult<PageVo<Menu>> findPage(
            @RequestParam(defaultValue = "1") int currentPage
            , @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Menu> page = menuService.getTreeData();
        return AxiosResult.success(page);
    }

    @GetMapping("{id}")
    public AxiosResult<Menu> findById(@PathVariable Long id) {
        return AxiosResult.success(menuService.findById(id));
    }

    @PostMapping
    public AxiosResult<Void> addEntity(@RequestBody Menu entity) {
        return toAxios(menuService.addEntity(entity));
    }

    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody Menu entity) {
        return toAxios(menuService.updateEntity(entity));
    }

    @DeleteMapping("{id}")
    public AxiosResult<Void> deleteById(@PathVariable Long id) {
        return toAxios(menuService.deleteById(id));
    }

    @GetMapping("getMenuTreeData")
    public AxiosResult<List<Menu>> getMenuTreeData(){
        PageVo<Menu> treeData = menuService.getTreeData();
        List<Menu> list = treeData.getList();
        return AxiosResult.success(list);
    }
}
