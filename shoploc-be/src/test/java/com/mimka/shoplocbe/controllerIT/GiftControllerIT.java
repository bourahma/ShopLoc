package com.mimka.shoplocbe.controllerIT;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GiftControllerIT extends AuthenticationControllerIT {

    @Test
    void testGetGifts_ReturnOK () throws Exception {
        mockMvc.perform(get("/gift/")
                        .header("Authorization", "Bearer " + customerJWTToken))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    void testGetCommerceGifts_ReturnOK () throws Exception {
        mockMvc.perform(get("/gift/2")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetCommerceGifts_WhenInvalidCommerceId_ReturnNoContent () throws Exception {
        mockMvc.perform(get("/gift/100")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetGiftsPerCommerceType_ReturnOK () throws Exception {
        mockMvc.perform(get("/gift/commerce-type/14")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    void testGetGiftsPerCommerceType_WhenInvalidCommerceType_ReturnNoContent () throws Exception {
        mockMvc.perform(get("/gift/commerce-type/100")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAvailGift_ReturnOK () throws Exception {
        mockMvc.perform(get("/gift/avail/1")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk());
    }

    @Test
    void testAvailGift_WhenInvalidGiftId_ReturnBadRequest () throws Exception {
        mockMvc.perform(get("/gift/avail/121")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCustomerGiftsHistory_ReturnOK () throws Exception {
        mockMvc.perform(get("/gift/history")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCustomerGiftsHistory_WhenCustomerHasNoGiftHistory_ReturnsOK () throws Exception {
        mockMvc.perform(get("/gift/history")
                        .header("Authorization", "Bearer " + notVFPCustomerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
