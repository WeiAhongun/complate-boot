package com.shangma.cn.common.valid.annotationhandler;

import com.shangma.cn.common.valid.annotation.SexSelector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class SelectorHandler implements ConstraintValidator<SexSelector,String> {
    private List<String> strs;
    //初始化
    @Override
    public void initialize(SexSelector constraintAnnotation) {
        String[] strList = constraintAnnotation.strs();
        strs = Arrays.asList(strList);
    }

    /**
     *
     * @param s 前端传来的值
     * @param constraintValidatorContext
     * @return 验证成功返回true 验证失败返回false
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return strs.contains(s);
    }
}
