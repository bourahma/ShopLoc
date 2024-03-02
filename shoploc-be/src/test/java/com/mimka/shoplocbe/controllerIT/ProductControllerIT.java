package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerIT extends AuthenticationControllerIT {

    @Test
    void testGetProduct_ReturnOK () throws Exception {
        mockMvc.perform(get("/product/detail/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productName").value("Pain au levain"))
                .andExpect(jsonPath("$.description").value("Délicieux pain croustillant"))
                .andExpect(jsonPath("$.price").value(3.5))
                .andExpect(jsonPath("$.quantity").value(150))
                .andExpect(jsonPath("$.rewardPointsPrice").value(3))
                .andExpect(jsonPath("$.gift").value(true))
                .andExpect(jsonPath("$.discountId").value(0))
                .andExpect(jsonPath("$.commerceId").value(1));
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateProduct_ReturnOK () throws Exception {
        ProductDTO productDTO = this.getProductDTO();

        mockMvc.perform(put("/product/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productName").value("Pain au levain"))
                .andExpect(jsonPath("$.description").value("Délicieux pain croustillant"))
                .andExpect(jsonPath("$.price").value(4))
                .andExpect(jsonPath("$.quantity").value(150))
                .andExpect(jsonPath("$.rewardPointsPrice").value(3.5))
                .andExpect(jsonPath("$.gift").value(false))
                .andExpect(jsonPath("$.discountId").value(0))
                .andExpect(jsonPath("$.commerceId").value(1));
    }

    @Test
    void testDeleteProduct_ReturnNoContent () throws Exception {
        mockMvc.perform(delete("/product/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @NotNull
    private ProductDTO getProductDTO () {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Pain au levain");
        productDTO.setDescription("Délicieux pain croustillant");
        productDTO.setPrice(4.0);
        productDTO.setQuantity(150);
        productDTO.setRewardPointsPrice(3.5);
        productDTO.setGift(false);

        return productDTO;
    }
}