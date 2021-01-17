package com.shangma.cn;

import com.shangma.cn.common.utils.MapUtils;
import com.shangma.cn.entity.Admin;

import java.util.Map;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        Admin admin = new Admin();
        admin.setAdminPassword("56465");
        admin.setAdminCode("546789789");
        admin.setAdminAddress("上海");
        admin.setAdminDept("IT部");
        Map<String, Object> objectToMap = MapUtils.getObjectToMap(admin);
        System.out.println(objectToMap);
    }
}
