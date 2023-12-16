package com.mimka.shoplocbe.controller.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerAdviceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authenticationFailed_whenPasswordIncorrect () throws Exception {
        var expectedJson = """
                {
                    "message": "Mot de passe incorrect."
                }
                """;
        var authJson = """
                {
                    "username": "Joe",
                    "password": "1234567849"
                }
                """;
        this.mockMvc.perform(post("/authentication/login")
                        .content(authJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void authenticationFailed_whenUsernameIncorrect () throws Exception {
        var expectedJson = """
                {
                    "message": "Nom d\\'utilisateur incorrect."
                }
                """;
        var authJson = """
                {
                    "username": "Jo",
                    "password": "123456784"
                }
                """;

        this.mockMvc.perform(post("/authentication/login")
                        .content(authJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void registrationExceptionHandler_whenUsernameAlreadyExists () throws Exception {
        var expectedJson = """
                {
                    "message": "Le nom d\\'utilisateur est déjà utilisé."
                }
                """;
        var authJson = """
                    {
                      "username": "Joe",
                      "lastname": "aziz",
                      "firstname": "bourahma",
                      "password": "12341234",
                      "confirmedPassword": "12341234",
                      "email": "az.az@gmail.com",
                      "phoneNumber": "0252727262"
                    }
                """;

        this.mockMvc.perform(post("/authentication/register")
                        .content(authJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void registrationExceptionHandler_whenEmailAlreadyExists () throws Exception {
        var expectedJson = """
                {
                    "message": "L\\'adresse e-mail est déjà associée à un compte existant."
                }
                """;
        var authJson = """
                    {
                      "username": "Joe",
                      "lastname": "aziz",
                      "firstname": "bourahma",
                      "password": "12341234",
                      "confirmedPassword": "12341234",
                      "email": "jane.smith@gmail.com",
                      "phoneNumber": "0252727262"
                    }
                """;

        this.mockMvc.perform(post("/authentication/register")
                        .content(authJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void registrationExceptionHandler_whenPasswordsAreDifferent () throws Exception {
        var expectedJson = """
                {
                    "message": "Les mots de passe sont différents."
                }
                """;
        var authJson = """
                    {
                      "username": "aziz",
                      "lastname": "aziz",
                      "firstname": "bourahma",
                      "password": "123412347",
                      "confirmedPassword": "123412349",
                      "email": "az.az@gmail.com",
                      "phoneNumber": "0252727262"
                    }
                """;

        this.mockMvc.perform(post("/authentication/register")
                        .content(authJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}
