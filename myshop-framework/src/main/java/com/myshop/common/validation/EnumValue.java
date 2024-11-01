package com.myshop.common.validation;

import com.myshop.common.validation.impl.EnumValueChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Chú thích xác thực giá trị enum
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValueChecker.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface EnumValue {

    String message() default "Phải là giá trị đã chỉ định";

    String[] strValues() default {};

    int[] intValues() default {};

    // groups
    Class<?>[] groups() default {};

    // payload
    Class<? extends Payload>[] payload() default {};


    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValue[] value();
    }
}