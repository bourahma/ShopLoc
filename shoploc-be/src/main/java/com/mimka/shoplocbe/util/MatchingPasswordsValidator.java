package com.mimka.shoplocbe.util;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        RegisterDTO userDTO = (RegisterDTO) value;
        String password = userDTO.getPassword();
        String confirmedPassword = userDTO.getConfirmedPassword();

        if (!password.equals(confirmedPassword)) {
            return true;
        }
        return false;
    }

    @Override
    public void initialize(MatchingPasswords annotation) {
    }
}
