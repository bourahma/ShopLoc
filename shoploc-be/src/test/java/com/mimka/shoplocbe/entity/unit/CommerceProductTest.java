package com.mimka.shoplocbe.entity.unit;


import com.mimka.shoplocbe.entity.Commerce;
import com.mimka.shoplocbe.entity.CommerceProduct;
import com.mimka.shoplocbe.entity.CommerceProductId;
import com.mimka.shoplocbe.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommerceProductTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        CommerceProduct commerceProduct = new CommerceProduct();
        CommerceProductId commerceProductId = new CommerceProductId(1L, 2L);
        Commerce commerce = new Commerce();
        Product product = new Product();
        Integer quantity = 10;

        commerceProduct.setId(commerceProductId);
        commerceProduct.setCommerce(commerce);
        commerceProduct.setProduct(product);
        commerceProduct.setQuantity(quantity);

        assertEquals(commerceProductId, commerceProduct.getId());
        assertEquals(commerce, commerceProduct.getCommerce());
        assertEquals(product, commerceProduct.getProduct());
        assertEquals(quantity, commerceProduct.getQuantity());
    }
}