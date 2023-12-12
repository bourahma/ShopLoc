package com.mimka.shoplocbe.controller.unit;

import com.mimka.shoplocbe.controller.CommerceController;
import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.service.CommerceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class CommerceControllerTest {

    @Mock
    private CommerceServiceImpl commerceServiceImpl;

    @InjectMocks
    private CommerceController commerceController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ProductController_ShouldBeAnnotated () {
        assertNotNull(CommerceController.class.getAnnotation(RestController.class));
    }

    @Test
    void productsByCommerce_ShouldBeAnnotated () throws NoSuchMethodException {
        var commercesMethod = CommerceController.class.getDeclaredMethod("commerces");
        var getMapping = commercesMethod.getAnnotation(GetMapping.class);
        assertNotNull(commercesMethod.getAnnotation(GetMapping.class));
        assertArrayEquals(new String[]{"/"}, getMapping.value());
    }

    @Test
    void commerces_shouldCallTheService() {
        commerceController.commerces();
        verify(commerceServiceImpl).getCommerces();
    }
}
