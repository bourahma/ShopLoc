package com.mimka.shoplocbe.util;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DigitsOnlyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DigitsOnly {
    String message() default "The input must contain only digits";
}
