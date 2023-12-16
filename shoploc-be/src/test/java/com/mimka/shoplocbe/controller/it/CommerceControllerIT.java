package com.mimka.shoplocbe.controller.it;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommerceControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private String accessToken;
    @BeforeEach
    void setUp() throws Exception {
        String authJson = """
                {
                    "username": "Joe",
                    "password": "12345678"
                }
                """;

        ResultActions resultActions = this.mockMvc.perform(post("/authentication/login")
                        .content(authJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        this.accessToken = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.access-token");    }

    @Test
    void commerces_ShouldReturnStatus200() throws Exception {
        System.out.println(accessToken);
        this.mockMvc.perform(get("/commerce/")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    void commerceProducts_ShouldReturnStatus200() throws Exception {
        this.mockMvc.perform(get("/commerce/4/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void commerceProducts_AllReturnedProductsBelongsToCommerce() throws Exception {
        this.mockMvc.perform(get("/commerce/4/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}