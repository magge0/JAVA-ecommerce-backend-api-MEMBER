package com.myshop.common.validation.impl;


import com.myshop.common.validation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Xác thực giá trị enum
 */
public class EnumValueChecker implements ConstraintValidator<EnumValue, Object> {

    private String[] stringValues;
    private int[] integerValues;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value instanceof String) {
            for (String s : stringValues) {
                if (s.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer) {
            for (int s : integerValues) {
                if (s == ((Integer) value).intValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        stringValues = constraintAnnotation.strValues();
        integerValues = constraintAnnotation.intValues();
    }
}