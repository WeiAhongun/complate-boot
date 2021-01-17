package com.shangma.cn.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.valid.group.AddGroup;
import com.shangma.cn.common.valid.group.UpdateGroup;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Role;
import com.shangma.cn.service.RoleService;
import com.shangma.cn.vo.PageVo;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:48
 * 文件说明：
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @GetMapping("getAllRole")
    public AxiosResult<List<Role>> getAllRole(){
        return AxiosResult.success(roleService.getAll());
    }

    @GetMapping
    public AxiosResult<PageVo<Role>> findPage(
            @RequestParam(defaultValue = "1") int currentPage
            , @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Role> page = roleService.findAll();
        return AxiosResult.success(page);
    }

    @GetMapping("{id}")
    public AxiosResult<Role> findById(@PathVariable Long id) {
        return AxiosResult.success(roleService.findRoleById(id));
    }

    @PostMapping
    public AxiosResult addEntity(@RequestBody @Validated(AddGroup.class) Role entity) throws IOException, TemplateException, IllegalAccessException, MessagingException {
        int i = roleService.addRoleAndMenu(entity);
        return toAxios(i);
    }

    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody @Validated(UpdateGroup.class) Role entity) {
        return toAxios(roleService.updateRoleAndMenu(entity));
    }

    @DeleteMapping("{id}")
    public AxiosResult<Void> deleteById(@PathVariable Long id) {
        return toAxios(roleService.deleteRoleAndMenuById(id));
    }


}
