package com.mimka.shoplocbe.controllerIT;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CommerceControllerIT extends ControllerIT {

    @Test
    public void testGetAllCommerces() throws Exception {
        mockMvc.perform(get("/commerce/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
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
    public void testGetCommerceById() throws Exception {
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
}
