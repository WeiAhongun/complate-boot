package com.shangma.cn.controller;

import com.shangma.cn.common.cache.RedisCacheUtils;
import com.shangma.cn.common.cache.TokenService;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.common.service.UploadService;
import com.shangma.cn.common.utils.CaptchaRedisUtils;
import com.shangma.cn.dto.LoginFormDTO;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.AdminExample;
import com.shangma.cn.mapper.AdminMapper;
import com.shangma.cn.service.AdminService;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController
public class CommonController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("avatarUpload")
    public AxiosResult<String> avatarUpload(@RequestPart Part avatar) throws IOException {
        String filename = avatar.getSubmittedFileName();
        String filename1 = UUID.randomUUID().toString().replaceAll("-","")+filename;
        return AxiosResult.success(uploadService.upload(avatar.getInputStream(),filename1));
    }
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/getCode")
    public AxiosResult captcha(@RequestParam String uuid){
        SpecCaptcha specCaptcha = new SpecCaptcha(120, 42, 5);
        String verCode = specCaptcha.text().toLowerCase();
        CaptchaRedisUtils.setCaptchaRedis(uuid,verCode);
        return AxiosResult.success(specCaptcha.toBase64());
    }
    @Autowired
    private AdminService adminService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenService tokenService;
    @PostMapping("doLogin")
    public AxiosResult doLogin(@RequestBody @Valid LoginFormDTO loginFormDTO){
        if(!CaptchaRedisUtils.getCaptchaRedis(loginFormDTO.getUuid()).equalsIgnoreCase(loginFormDTO.getCaptcha())){
            return AxiosResult.error(AxiosStatus.ERROR_CATPCHA);
        }
        Admin admin = adminService.getAdminByAccount(loginFormDTO.getUsername());
        if(admin == null){
            return AxiosResult.error(AxiosStatus.ERROR_ADMIN);
        }
        boolean matches = bCryptPasswordEncoder.matches(loginFormDTO.getPassword(), admin.getAdminPassword());
        if(!matches){
            return AxiosResult.error(AxiosStatus.ERROR_PASSWORD);
        }
        return AxiosResult.success(tokenService.createToken(admin));
    }
}
