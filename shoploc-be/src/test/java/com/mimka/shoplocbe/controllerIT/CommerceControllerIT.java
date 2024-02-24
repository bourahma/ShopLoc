package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.commerce.AddressDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class CommerceControllerIT extends AuthenticationControllerIT {
    @Test
    void testGetAllCommerce_ReturnOK () throws Exception {
        mockMvc.perform(get("/commerce/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].commerceName").value("Boulangerie du Coin"))
                .andExpect(jsonPath("$[1].commerceName").value("Le Petit Café"))
                .andExpect(jsonPath("$[2].commerceName").value("Pizzeria Bella Napoli"))
                .andExpect(jsonPath("$[3].commerceName").value("Magasin Magique"))
                .andExpect(jsonPath("$[4].commerceName").value("Café des Artistes"))
                .andExpect(jsonPath("$[5].commerceName").value("Fleuriste Parfumé"))
                .andExpect(jsonPath("$[6].commerceName").value("Artisan du Bois"))
                .andExpect(jsonPath("$[7].commerceName").value("Délice du Café"));
    }

    @Test
    void testGetCommerceById_ReturnOk () throws Exception {
        mockMvc.perform(get("/commerce/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.commerceId").value(1))
                .andExpect(jsonPath("$.commerceName").value("Boulangerie du Coin"))
                .andExpect(jsonPath("$.openingHour").value("08:00:00"))
                .andExpect(jsonPath("$.closingHour").value("18:00:00"))
                .andExpect(jsonPath("$.imageUrl").value("https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/mae-mu-_h-2jrL9cMU-unsplash.jpg"));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateCommerce_WhenAllFieldsFormAreValid_ReturnCreated () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.commerceId").value(not(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commerceName").value("Boulangerie du Coin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openingHour").value("08:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closingHour").value("18:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value("URL:/commerce-image"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.street").value("1 Rue de la Clef"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.postalCode").value(59000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.longitude").value(not(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.latitude").value(not(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.city").value("Lille"));
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCommerce_WhenAllFieldsFormAreValid_ReturnOk () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();

        mockMvc.perform(MockMvcRequestBuilders.put("/commerce/1")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.commerceId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commerceName").value("Boulangerie du Coin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openingHour").value("08:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closingHour").value("18:00:00"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value("URL:/commerce-image"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.street").value("1 Rue de la Clef"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.postalCode").value(59000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.longitude").value(not(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.latitude").value(not(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressDTO.city").value("Lille"));
    }
    // TODO : Test update with invalid commerce dto fields.


    @Test
    void testUpdateCommerce_WhenInvalidCommerce_ReturnNoContent () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        mockMvc.perform(MockMvcRequestBuilders.put("/commerce/100")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateCommerce_WithBlankCommerceName_ReturnBadRequest () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        commerceDTO.setCommerceName(" ");

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Le nom du commerce est requis"));
    }


    @Test
    public void testCreateCommerce_WithInvalidOpeningHour_ReturnBadRequest () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        commerceDTO.setOpeningHour(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("L'heure d'ouverture est requise"));
    }
    @Test
    public void testCreateCommerce_WithInvalidclosingHour_ReturnBadRequest () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        commerceDTO.setClosingHour(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("L'heure de fermeture est requise"));
    }

    @Test
    public void testCreateCommerce_WithInvalidaddressDTO_ReturnBadRequest () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        commerceDTO.setAddressDTO(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Les informations d'adresse sont requises"));
    }

    @Test
    public void testCreateCommerce_WithInvalidaddressDTOstreet_ReturnBadRequest () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        commerceDTO.getAddressDTO().setStreet(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Le nom de la rue est requis"));
    }

    @Test
    public void testCreateCommerce_WithInvalidaddressDTOcity_ReturnBadRequest () throws Exception {
        CommerceDTO commerceDTO = this.getCommerceDTO();
        commerceDTO.getAddressDTO().setCity(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(commerceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("La ville est requise"));
    }
    @Test
    void testGetCommerceById_WhenIdDoesNotExist_ReturnsNoContent () throws Exception {
        mockMvc.perform(get("/commerce/100")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Commerce not found for ID : 100"));
    }

    @Test
    void testGetCommerceProducts_WhenIdCommerceDoNotExist_ReturnsNoContent () throws Exception {
        mockMvc.perform(get("/commerce/100/products")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Commerce not found for ID : 100"));
    }

    @Test
    void testGetCommerceProducts_WhenIdCommerceDoExist_ReturnOk () throws Exception {
        mockMvc.perform(get("/commerce/1/products")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    @Rollback
    void testAddProduct_WhenIdCommerceDoExist_ReturnCreated () throws Exception {
        ProductDTO productDTO = this.getProductDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/1")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/commerce/1/products")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(3)));
    }
    // TODO : Move this test to product controller it and do needed actions on the code.
    @Test
    @Transactional
    @Rollback
    void testDisableCommerce_ReturnOk () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/commerce/1")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/commerce/1")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Commerce not found for ID : 1"));
    }
    // TODO : Handle delete commerce.

    // Methods :
    @NotNull
    private CommerceDTO getCommerceDTO () {
        CommerceDTO commerceDTO = new CommerceDTO();

        commerceDTO.setCommerceName("Boulangerie du Coin");
        commerceDTO.setOpeningHour(LocalTime.of(8, 0));
        commerceDTO.setClosingHour(LocalTime.of(18, 0));

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("1 Rue de la Clef");
        addressDTO.setPostalCode(59000);
        addressDTO.setCity("Lille");
        commerceDTO.setAddressDTO(addressDTO);

        return commerceDTO;
    }

    @NotNull
    private ProductDTO getProductDTO () {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductName("Croissant");
        productDTO.setDescription("Freshly baked croissant");
        productDTO.setPrice(1.5);
        productDTO.setQuantity(100);
        productDTO.setRewardPointsPrice(1.0);
        productDTO.setGift(false);

        return productDTO;
    }
}
