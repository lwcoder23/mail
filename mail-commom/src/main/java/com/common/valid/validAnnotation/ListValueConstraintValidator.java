package com.common.valid.validAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private Set<Integer> set = new HashSet<>();

    // 初始化。获取校验规则
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        for (int i :values) {
            set.add(i);
        }
    }

    // 判断是否校验成功，是否匹配校验规则
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // value 需要校验的值
        return set.contains(value);
    }
}
