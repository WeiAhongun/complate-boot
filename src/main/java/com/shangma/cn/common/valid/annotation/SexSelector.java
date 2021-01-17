package com.shangma.cn.common.valid.annotation;

import com.shangma.cn.common.valid.annotationhandler.SelectorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = {SelectorHandler.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SexSelector {

    String message() default "{javax.validation.constraints.SexSelector.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //自定义数组，我们选择的值必须在这个数组中
    String[] strs() default {};
}
