package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.exception.RegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    private String pIncorrect = "Mot de passe incorrect.";

    private String message = "message";

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> authenticationFailed(BadCredentialsException exception) {
        if (!exception.getMessage().contains("Nom d\'utilisateur incorrect.")) {
            return Map.of(message, pIncorrect);
        }
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> authenticationFailed(AuthenticationException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentValidation(MethodArgumentNotValidException exception) {
        return Map.of(message, exception.getMessage());
    }

    @ExceptionHandler(value = RegistrationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Map<String, String> registrationExceptionHandler(RegistrationException exception) {
        return Map.of(message, exception.getMessage());
    }
}
