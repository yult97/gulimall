package com.atguigu.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验显示状态数组注解
 *
 * @author yubo
 */
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ListenerVal {
    //返回校验不通过提示信息
    String message() default "{com.atguigu.common.valid.ListenerVal.message}";

    //分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] val() default {};
}
