package com.mimka.shoplocbe.controller.unit;

import com.mimka.shoplocbe.controller.AuthenticationController;
import com.mimka.shoplocbe.controller.ControllerAdvice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ControllerAdviceTest {
    @InjectMocks
    private ControllerAdvice controllerAdvice;

    @Test
    void controllerAdvice_ShouldBeAnnotated () {
        assertNotNull(ControllerAdvice.class.getAnnotation(RestControllerAdvice.class));

    }
}
