package com.shangma.cn.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.pool.AsyncFactory;
import com.shangma.cn.common.pool.AsyncManager;
import com.shangma.cn.common.utils.EmailUtils;
import com.shangma.cn.common.utils.MapUtils;
import com.shangma.cn.common.utils.TemplateUtils;
import com.shangma.cn.common.valid.group.AddGroup;
import com.shangma.cn.common.valid.group.UpdateGroup;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.Brand;
import com.shangma.cn.entity.Role;
import com.shangma.cn.service.AdminService;
import com.shangma.cn.service.BrandService;
import com.shangma.cn.vo.PageVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:48
 * 文件说明：
 */
@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping
    public AxiosResult<PageVo<Admin>> findPage(
            @RequestParam(defaultValue = "1") int currentPage
            , @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Admin> page = adminService.findAll();
        return AxiosResult.success(page);
    }

    @GetMapping("{id}")
    public AxiosResult<Admin> findById(@PathVariable Long id) {
        return AxiosResult.success(adminService.findById(id));
    }

    @PostMapping
    public AxiosResult addEntity(@RequestBody @Validated(AddGroup.class) Admin entity) throws IOException, TemplateException, IllegalAccessException, MessagingException {
        entity.setAdminPassword(bCryptPasswordEncoder.encode("123456"));
        int i = adminService.addAdminAndRole(entity);
        Map<String, Object> map = MapUtils.getObjectToMap(entity);
        String templateStr = TemplateUtils.getTemplateStr("main.ftlh", map);
        AsyncManager.getInstance().execute(AsyncFactory.sendEmail("阿里嘎巴码云<a1293233730@163.com>",entity.getAdminEmail(),"激活验证",templateStr));
        return toAxios(i);
    }

    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody @Validated(UpdateGroup.class) Admin entity) {
        return toAxios(adminService.updateAdminAndRole(entity));
    }

    @DeleteMapping("{id}")
    public AxiosResult<Void> deleteById(@PathVariable Long id) {
        return toAxios(adminService.deleteById(id));
    }

    @GetMapping("export")
    public ResponseEntity<byte[]> export() throws IOException {
        PageVo<Admin> all = adminService.findAll();
        List<Admin> list = all.getList();
        list.forEach(admin -> {
            try {
                //给图片URL赋值
                admin.setUrl(new URL(admin.getAdminAvatar()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        EasyExcel.write(stream,Admin.class).sheet("员工列表").doWrite(list);
        byte[] bytes = stream.toByteArray();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", URLEncoder.encode("员工列表.xlsx", "utf-8"));
        ResponseEntity responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.OK);
        stream.close();
        return responseEntity;
    }

    @GetMapping("{id}/roles")
    public AxiosResult<List<Role>> getAdminRole(@PathVariable long id){
        List<Role> roles = adminService.getAdminRole(id);
        return AxiosResult.success(roles);
    }

    @DeleteMapping("{adminId}/roleId/{rid}")
    public AxiosResult deleteAdminRole(@PathVariable long adminId,@PathVariable long rid){
        return AxiosResult.success(adminService.deleteAdminRole(adminId,rid));
    }

    @GetMapping("getAdminInfo")
    public AxiosResult<Map<String,Object>> getAdminInfo(){
        Map<String,Object> map = adminService.getAdminInfo();
        return AxiosResult.success(map);
    }
}
