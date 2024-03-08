package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    private String pIncorrect = "Mot de passe incorrect.";

    private String message = "message";

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> authenticationFailed(BadCredentialsException exception) {
        if (!exception.getMessage().contains("Aucun client n'est associé à ce nom d'utilisateur.")) {
            return Map.of(message, pIncorrect);
        }
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> authenticationFailed(AuthenticationException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = RegistrationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Map<String, String> registrationExceptionHandler(RegistrationException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = CommerceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Map<String, String> CommerceNotFoundExceptionHandler(CommerceNotFoundException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        return Map.of(message, ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed"));
    }

    @ExceptionHandler(value = RegistrationTokenInvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> registrationTokenInvalidExceptionHandler(RegistrationTokenInvalidException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = CommerceTypeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Map<String, String> CommerceTypeNotFoundExceptionHandler(CommerceTypeNotFoundException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> InsufficientFundsExceptionHandler(InsufficientFundsException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = InvalidCreditAmountException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> InvalidCreditAmountExceptionHandler(InvalidCreditAmountException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = ProductCategoryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Map<String, String> ProductCategoryNotFoundExceptionHandler(ProductCategoryNotFoundException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = BenefitException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Map<String, String> authenticationFailed(BenefitException exception) {
        return Map.of(message, exception.getMessage());
    }
}
