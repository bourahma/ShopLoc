package com.mimka.shoplocbe.controller.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mimka.shoplocbe.controller.CommerceController;
import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.service.CommerceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommerceControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommerceController commerceController;

    @Autowired
    private CommerceServiceImpl commerceService;

    private String accessToken;
    @BeforeEach
    void setUp() throws JsonProcessingException {
        String username = "Joe";
        String password = "12345678";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String authEndpoint = "http://localhost:" + port + "/authentication/login";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(authEndpoint, requestEntity, String.class);
        Map<String, String> responseMap = new ObjectMapper().readValue(responseEntity.getBody(), new TypeReference<Map<String, String>>() {});
        this.accessToken = responseMap.get("access-token");
    }

    // Test with RestTemplate.
    @Test
    void testRestTemplate_ShouldBeInstantiated() {
        assertNotNull(commerceController);
    }

    @Test
    void commerces_ShouldReturnStatus200() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/commerce/",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void commerces_ShouldReturnAList() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/commerce/",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class);

        List<CommerceDTO> commerces = responseEntity.getBody();
        assertNotNull(commerces);
    }
}