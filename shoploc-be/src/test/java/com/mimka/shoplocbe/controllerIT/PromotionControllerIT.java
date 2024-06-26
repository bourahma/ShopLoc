package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PromotionControllerIT extends ControllerIT {

    @Test
    @Transactional
    void testCreateOfferPromotion_ReturnCreated () throws Exception {
        mockMvc.perform(post("/promotion/offer/8")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOfferPromotionDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    void testCreateDiscountPromotion_ReturnCreated () throws Exception {
        mockMvc.perform(post("/promotion/discount/8")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getDiscountPromotionDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetPromotion_ReturnOk () throws Exception {
        mockMvc.perform(get("/promotion/1")
                        .header("Authorization", "Bearer " + merchantJWTToken))
                .andExpect(status().isOk());
    }

    @NotNull
    private PromotionDTO getOfferPromotionDTO () {
        PromotionDTO offerPromotionDTO = new PromotionDTO();
        offerPromotionDTO.setStartDate(LocalDate.of(2024, 4, 1));
        offerPromotionDTO.setEndDate(LocalDate.of(2024, 4, 30));
        offerPromotionDTO.setDescription("Achetez 3, obtenez-en 1 gratuitement");
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
        discountPromotionDTO.setCommerceId(5L);
        discountPromotionDTO.setDiscountPercent(20);

        return discountPromotionDTO;
    }
}
