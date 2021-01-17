package com.shangma.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shangma.cn.common.valid.annotation.SexSelector;
import com.shangma.cn.common.valid.group.AddGroup;
import com.shangma.cn.common.valid.group.UpdateGroup;
import com.shangma.cn.entity.base.BaseEntity;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ColumnWidth(30)//每列的宽
@HeadRowHeight(40)//第一行的高
@ContentRowHeight(40)//第一行之外的每一行的高度
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
@HeadFontStyle(fontHeightInPoints = 20)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)//居中显示
@ContentFontStyle(fontHeightInPoints = 20)
public class Admin extends BaseEntity {

    @NotBlank(groups = {UpdateGroup.class, AddGroup.class},message = "账号为空")
    @Pattern(groups = {UpdateGroup.class, AddGroup.class},regexp = "^[a-zA-Z][a-zA-Z0-9_]{6,10}$", message = "账号格式错误")
    @ExcelProperty("用户账号")
    private String adminAccount;

    @ExcelProperty("用户昵称")
    @NotBlank(groups = {UpdateGroup.class, AddGroup.class},message = "昵称不能为空")
    private String nickName;

    @Email(groups = {UpdateGroup.class, AddGroup.class},message = "邮箱错误")
    @NotBlank
    @ExcelProperty("用户邮箱")
    private String adminEmail;

    @ExcelProperty("用户手机号")
    @Pattern(groups = {UpdateGroup.class, AddGroup.class},regexp = "^(13[0-9]|14[01456879]|15[0-3,5-9]|16[2567]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$", message = "手机号格式错误")
    @NotBlank(groups = {UpdateGroup.class, AddGroup.class},message = "手机号为空")
    private String adminPhone;

    @ExcelIgnore
    @SexSelector(groups = {UpdateGroup.class, AddGroup.class},strs = {"0", "1", "2"}, message = "性别的属性值必须为012")
    private String adminSex;

    @ExcelIgnore
    @URL(groups = {UpdateGroup.class, AddGroup.class},message = "url地址错误")
    @NotBlank
    private String adminAvatar;

    @ExcelIgnore
    @Pattern(groups = {UpdateGroup.class, AddGroup.class},regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "身份证格式错误")
    @NotBlank(groups = {UpdateGroup.class, AddGroup.class},message = "身份证为空")
    private String adminCode;

    @ExcelIgnore
    private Boolean adminStatus;

    @ExcelIgnore
    private String adminName;

    @ExcelIgnore
    @JsonInclude
    private String adminPassword;

    @ExcelIgnore
    private String adminAddress;

    @ExcelIgnore
    private BigDecimal adminSalary;

    @ExcelIgnore
    private String adminDept;

    @ExcelIgnore
    private Boolean delFlag;

    @ExcelProperty("用户头像")
    @ColumnWidth(7)
    private java.net.URL url;

    @ExcelIgnore
    private List<Long> roleIds;
}
