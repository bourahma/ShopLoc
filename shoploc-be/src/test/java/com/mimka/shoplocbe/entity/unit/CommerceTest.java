package com.mimka.shoplocbe.entity.unit;

import com.mimka.shoplocbe.entity.Commerce;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CommerceTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        Commerce commerce = new Commerce();
        Long commerceId = 1L;
        String commerceName = "TestCommerce";
        LocalTime openingHour = LocalTime.of(8, 0);
        LocalTime closingHour = LocalTime.of(18, 0);

        commerce.setCommerceId(commerceId);
        commerce.setCommerceName(commerceName);
        commerce.setOpeningHour(openingHour);
        commerce.setClosingHour(closingHour);

        assertEquals(commerceId, commerce.getCommerceId());
        assertEquals(commerceName, commerce.getCommerceName());
        assertEquals(openingHour, commerce.getOpeningHour());
        assertEquals(closingHour, commerce.getClosingHour());
    }

    @Test
    void toString_ShouldReturnExpectedString() {
        Commerce commerce = new Commerce(1L, "TestCommerce", LocalTime.of(8, 0), LocalTime.of(18, 0));

        assertTrue(commerce.getCommerceId()==1);
        assertTrue(commerce.getCommerceName().equals("TestCommerce"));
        assertTrue(commerce.getOpeningHour().equals(LocalTime.of(8, 0)));
        assertTrue(commerce.getClosingHour().equals(LocalTime.of(18, 0)));
    }
}

