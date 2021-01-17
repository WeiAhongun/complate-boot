package com.shangma.cn.common.http;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/1 12:18
 * 文件说明：
 */

public enum AxiosStatus {

    OK(20000,"操作成功"),
    ERROE(50000,"操作失败"),
    FORM_VALID_FAIL(30000,"表单验证失败" ),
    IMG_TOMAX(30001,"文件过大"),
    IMG_ERROR(30002,"图片错误" ),
    ERROR_CATPCHA(30003,"验证码错误" ),
    ERROR_ADMIN(30004, "用户名错误"),
    ERROR_PASSWORD(30005,"密码错误" ),
    TOKEN_ERROR(33333,"认证用户失败"),
    LOGINTIME_EXPRIE(33333,"登入时间失效");


    AxiosStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private  int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
