package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PromotionControllerIT extends AuthenticationControllerIT {

    @Test
    void testGetAllCommercePromotions_ReturnOK () throws Exception {
        mockMvc.perform(get("/promotion/6")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCreateOfferPromotion_ReturnCreated () throws Exception {
        mockMvc.perform(post("/promotion/offer")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOfferPromotionDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateDiscountPromotion_ReturnCreated () throws Exception {
        mockMvc.perform(post("/promotion/discount")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getDiscountPromotionDTO())))
                .andExpect(status().isCreated());
    }

    @NotNull
    private PromotionDTO getOfferPromotionDTO () {
        PromotionDTO offerPromotionDTO = new PromotionDTO();
        offerPromotionDTO.setStartDate(LocalDate.of(2024, 4, 1));
        offerPromotionDTO.setEndDate(LocalDate.of(2024, 4, 30));
        offerPromotionDTO.setDescription("Achetez 3, obtenez-en 1 gratuitement");
        offerPromotionDTO.setProductId(8L);
        offerPromotionDTO.setCommerceId(8L);
        offerPromotionDTO.setRequiredItems(3);
        offerPromotionDTO.setOfferedItems(1);

        return offerPromotionDTO;
    }

    @NotNull
    private PromotionDTO getDiscountPromotionDTO () {
        PromotionDTO discountPromotionDTO = new PromotionDTO();
        discountPromotionDTO.setStartDate(LocalDate.of(2024, 3, 1));
        discountPromotionDTO.setEndDate(LocalDate.of(2024, 5, 15));
        discountPromotionDTO.setDescription("Vente de printemps");
        discountPromotionDTO.setProductId(12L);
        discountPromotionDTO.setCommerceId(5L);
        discountPromotionDTO.setDiscountPercent(20);

        return discountPromotionDTO;
    }
}
