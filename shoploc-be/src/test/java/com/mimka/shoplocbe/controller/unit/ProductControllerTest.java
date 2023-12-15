package com.mimka.shoplocbe.controller.unit;

import com.mimka.shoplocbe.controller.AuthenticationController;
import com.mimka.shoplocbe.controller.ProductController;
import com.mimka.shoplocbe.service.ProductServiceImpl;
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

class ProductControllerTest {

    @Mock
    private ProductServiceImpl productServiceImpl;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ProductController_ShouldBeAnnotated () {
        assertNotNull(AuthenticationController.class.getAnnotation(RestController.class));
    }

    @Test
    void productsByCommerce_ShouldBeAnnotated () throws NoSuchMethodException {
        var productsByCommerceMethod = ProductController.class.getDeclaredMethod("productsByCommerce", Long.class);
        var getMapping = productsByCommerceMethod.getAnnotation(GetMapping.class);
        assertNotNull(productsByCommerceMethod.getAnnotation(GetMapping.class));
        assertArrayEquals(new String[]{"/{commerceId}"}, getMapping.value());
    }

    @Test
    void commerces_shouldCallTheService() {
        productController.productsByCommerce(4L);
        verify(productServiceImpl).getProductsByCommerce(4L);
    }
}
