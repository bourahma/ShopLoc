package com.mimka.shoplocbe.controllerIT;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerIT {

    protected String customerJWTToken;
    protected String merchantJWTToken;
    protected String administratorJWTToken;

    @Autowired
    protected MockMvc mockMvc;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
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

    @BeforeAll
    public void setup() throws Exception {
        customerJWTToken = authenticateUser("Joe", "12345678", "customer");
        merchantJWTToken = authenticateUser("Loris", "12345678", "merchant");
        administratorJWTToken = authenticateUser("Jane", "12345678", "administrator");
    }
}