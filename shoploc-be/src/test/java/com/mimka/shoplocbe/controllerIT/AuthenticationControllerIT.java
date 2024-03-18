package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.user.AuthDTO;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerIT extends ControllerIT {

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
