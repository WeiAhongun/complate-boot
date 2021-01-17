package com.shangma.cn.entity;

import com.shangma.cn.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class Menu extends BaseEntity {

    private String menuName;

    private Long parentId;

    private Integer sorted;

    private String routerPath;

    private String componentPath;

    private String menuType;

    private String permsSign;

    private String menuIcon;

    private String requestUrl;

    private String requestMethod;

    private List<Menu> children;

}
