package com.mimka.shoplocbe.controllerIT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class AuthenticationControllerIT extends ControllerIT {

    protected String customerJWTToken;

    protected String merchantJWTToken;

    protected String administratorJWTToken;


    @Autowired
    protected MockMvc mockMvc;


    @Autowired
    protected ObjectMapper objectMapper;


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );


    @BeforeAll
    public void setup() throws Exception {
        postgres.start();

        customerJWTToken = authenticateUser("Joe", "12345678", "customer");
        merchantJWTToken = authenticateUser("Loris", "12345678", "merchant");
        administratorJWTToken = authenticateUser("Jane", "12345678", "administrator");
    }


    @AfterAll
    static void afterAll() {
        postgres.stop();
    }


    private String authenticateUser (String username, String password, String userType) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authentication/" + userType + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.access-token").exists())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        String accessToken = JsonPath.read(jsonResponse, "$.access-token");

        return accessToken;
    }
}
