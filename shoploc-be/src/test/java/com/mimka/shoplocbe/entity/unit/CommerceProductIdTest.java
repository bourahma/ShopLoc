package com.mimka.shoplocbe.entity.unit;

import com.mimka.shoplocbe.entity.CommerceProductId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommerceProductIdTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        Long commerceId = 1L;
        Long productId = 2L;

        CommerceProductId commerceProductId = new CommerceProductId();
        commerceProductId.setCommerceId(commerceId);
        commerceProductId.setProductId(productId);

        assertEquals(commerceId, commerceProductId.getCommerceId());
        assertEquals(productId, commerceProductId.getProductId());
    }
}
