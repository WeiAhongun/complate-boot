package com.shangma.cn.entity.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.shangma.cn.common.cache.TokenService;
import com.shangma.cn.common.container.SpringBeanUtils;
import com.shangma.cn.common.valid.group.AddGroup;
import com.shangma.cn.common.valid.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:44
 * 文件说明：
 */
@Data
public class BaseEntity {
    @Null(message = "id必须为空",groups = {AddGroup.class})
    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @ExcelIgnore
    private Long id;

    @ExcelProperty("创建时间")
    @DateTimeFormat(value = "yyyy年MM月dd日 HH:mm:ss")
    private Date addTime;
    @ExcelIgnore
    private Long creatorId;
    @ExcelIgnore
    private Date updateTime;
    @ExcelIgnore
    private Long updateId;


    public void setData() {
        TokenService bean = SpringBeanUtils.getBean(TokenService.class);
        if (id == null) {
            this.creatorId = bean.getLoginAdminId();
            this.addTime = new Date();
        } else {
            this.updateId = bean.getLoginAdminId();
            this.updateTime = new Date();
        }
    }

}
