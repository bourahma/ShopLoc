package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.HandleMailSendException;
import com.mimka.shoplocbe.exception.UserPasswordException;
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

    @ExceptionHandler(value = EmailAlreadyUsedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> authenticationFailed(EmailAlreadyUsedException exception) {
        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> authenticationFailed(BadCredentialsException exception) {
        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> authenticationFailed(AuthenticationException exception) {
        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(value = HandleMailSendException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> registrationFailed(HandleMailSendException exception) {
        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentValidation(MethodArgumentNotValidException exception) {
        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(value = UserPasswordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> passwordsDontMatch(UserPasswordException exception) {
        return Map.of("message", exception.getMessage());
    }
}
