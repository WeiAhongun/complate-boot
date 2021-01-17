package com.shangma.cn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoginAdmin {
    private String uuid;
    private Admin admin;
    private long loginTime;
    private long expireTime;
}
