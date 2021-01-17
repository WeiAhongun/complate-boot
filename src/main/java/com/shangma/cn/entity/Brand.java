package com.shangma.cn.entity;

import com.shangma.cn.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Brand  extends BaseEntity {


    private String brandName;

    private String brandSite;

    private String brandDesc;

    private String brandLogo;


}