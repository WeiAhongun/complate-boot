package com.shangma.cn.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginFormDTO {
    @NotBlank(message = "用户名为空")
    private String username;
    @NotBlank(message = "密码为空")
    private String password;
    @NotBlank(message = "验证码失效")
    private String uuid;
    @NotBlank(message = "验证码为空")
    private String captcha;
}
