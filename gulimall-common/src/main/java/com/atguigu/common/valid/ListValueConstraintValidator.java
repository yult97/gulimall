package com.atguigu.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 校验显示状态输入值
 * @author yubo
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListenerVal, Integer> {
    Set<Integer> set = new HashSet();

    @Override
    public void initialize(ListenerVal constraintAnnotation) {

        //获取传递参数值
        int[] val = constraintAnnotation.val();
        for (int i : val
        ) {
            set.add(i);
        }
    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //判断传入值和判断值是否想通
        return set.contains(value);
    }
}
