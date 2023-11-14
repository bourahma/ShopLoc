package com.mimka.shoplocbe.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DigitsOnlyValidator implements ConstraintValidator<DigitsOnly, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return Pattern.matches("\\d+", value);
    }

    @Override
    public void initialize(DigitsOnly annotation) {
    }
}
