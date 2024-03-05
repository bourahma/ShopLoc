package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductCategoryControllerIT extends AuthenticationControllerIT {
    @Test
    void testGetCommerceProductCategories_ReturnOK () throws Exception {
        mockMvc.perform(get("/product/categories/1")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void testGetProductCategory_WhenProductCategoryIdDoExist_ReturnOK () throws Exception {
        mockMvc.perform(get("/product/category/1")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCategoryId").value(1))
                .andExpect(jsonPath("$.commerceId").value(1))
                .andExpect(jsonPath("$.label").value("Pain"))
                .andExpect(jsonPath("$.description").value("Variété de pains frais et croustillants."));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateProductCategory_ReturnCreated () throws Exception {
        mockMvc.perform(post("/product/category/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getProductCategoryDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productCategoryId").value(1001))
                .andExpect(jsonPath("$.commerceId").value(1))
                .andExpect(jsonPath("$.label").value("Label"))
                .andExpect(jsonPath("$.description").value("Product description"));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateProductCategory_WhenCommerceDoNotExist_ReturnNoContent () throws Exception {
        ProductCategoryDTO productCategoryDTO = this.getProductCategoryDTO();
        productCategoryDTO.setCommerceId(100L);
        productCategoryDTO.setLabel("label");
        productCategoryDTO.setDescription("desciption");

        mockMvc.perform(post("/product/category/100")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(productCategoryDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateProductCategory_ReturnOk () throws Exception {
        mockMvc.perform(put("/product/category/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getProductCategoryDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCategoryId").value(1000))
                .andExpect(jsonPath("$.commerceId").value(1))
                .andExpect(jsonPath("$.label").value("Label"))
                .andExpect(jsonPath("$.description").value("Product description"));
    }

    @Test
    void testUpdateProductCategory_ReturnBadRequest () throws Exception {
        mockMvc.perform(put("/product/category/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(new ProductCategoryDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetProductCategory_WhenProductCategoryIdDoNotExist_ReturnNoContent () throws Exception {
        mockMvc.perform(get("/product/category/100")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isNoContent());
    }

    @NotNull
    private ProductCategoryDTO getProductCategoryDTO () {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();

        productCategoryDTO.setCommerceId(1L);
        productCategoryDTO.setLabel("Label");
        productCategoryDTO.setDescription("Product description");

        return productCategoryDTO;
    }

}
