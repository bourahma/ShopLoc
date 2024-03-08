package com.mimka.shoplocbe.controllerIT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mimka.shoplocbe.dto.user.AuthDTO;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerIT extends ControllerIT {

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

    @Test
    void testCustomerAuthentication_WithInvalidUsername_ReturnUnauthorized () throws Exception {
        AuthDTO authDTO = this.getAuthenticationDTO();
        authDTO.setUsername("Jo");

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Aucun client n'est associé à ce nom d'utilisateur."));
    }

    @Test
    void testCustomerAuthentication_WithEmptyUsername_ReturnBadRequest () throws Exception {
        AuthDTO authDTO = this.getAuthenticationDTO();
        authDTO.setUsername(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le nom d'utilisateur ne peut pas être vide."));
    }

    @Test
    void testCustomerAuthentication_WithIncorrectPassword_ReturnUnauthorized () throws Exception {
        AuthDTO authDTO = this.getAuthenticationDTO();
        authDTO.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Mot de passe incorrect."));
    }

    @Test
    void testCustomerAuthentication_WithInvalidPassword_ReturnBadRequest () throws Exception {
        AuthDTO authDTO = this.getAuthenticationDTO();
        authDTO.setPassword("passwo");

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .header("Authorization", "Bearer " + merchantJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le mot de passe doit contenir entre 8 et 20 caractères."));
    }

    @NotNull
    private AuthDTO getAuthenticationDTO () {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("Joe");
        authDTO.setPassword("12345678");

        return authDTO;
    }
}
