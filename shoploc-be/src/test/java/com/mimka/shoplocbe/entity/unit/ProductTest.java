package com.mimka.shoplocbe.entity.unit;

import com.mimka.shoplocbe.entity.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        Product product = new Product();
        Long productId = 1L;
        String productName = "TestProduct";
        String description = "Product description";
        double price = 19.99;
        Integer quantity = 100;

        product.setProductId(productId);
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);

        assertEquals(productId, product.getProductId());
        assertEquals(productName, product.getProductName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice(), 0.001); // Add delta for double comparison
        assertEquals(quantity, product.getQuantity());
    }
}
