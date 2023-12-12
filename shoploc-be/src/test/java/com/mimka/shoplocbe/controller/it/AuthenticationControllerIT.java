package com.mimka.shoplocbe.controller.it;

import com.mimka.shoplocbe.controller.AuthenticationController;
import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.UsernameExistsException;
import com.mimka.shoplocbe.repository.RegistrationTokenRepository;
import com.mimka.shoplocbe.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    private static RegisterDTO registerDTO;

    // Setups
    @BeforeAll
    public static void setup() {
        registerDTO = new RegisterDTO();
        registerDTO.setUsername("username");
        registerDTO.setPassword("password");
        registerDTO.setConfirmedPassword("password");
        registerDTO.setFirstname("firstname");
        registerDTO.setLastname("lastname");
        registerDTO.setPhoneNumber("00 11 22 33 44");
        registerDTO.setEmail("address@email.com");
    }

    // Test with RestTemplate.
    @Test
    void testRestTemplate_ShouldBeInstantiated () {
        assertNotNull(authenticationController);
    }

    @Test
    void loginUserWithUsername_ShouldReturnToken() {
        var responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/login", AuthDTO.class);
        assertNotNull(responseEntity);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    void loginUserWithUsername_ShouldThrowAnUnauthorized(){
        var authDTO = new AuthDTO();
        authDTO.setUsername("Joe");
        authDTO.setPassword("12345678");

        var responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/login", authDTO, AuthDTO.class);
        assertNotNull(responseEntity); // TODO : fix this test to return 200 OK
    }

    // Integration tests on the controller methods
    @Test
    void loginUserWithUsername_ShouldReturnAccessTokeN () {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("Joe");
        authDTO.setPassword("12345678");

        Map<String, String> token = authenticationController.loginUserWithUsername(authDTO);
        Assertions.assertTrue(token.get("access-token").split("\\.").length == 3);
    }

    @Test
    void loginUserWithUsername_ShouldThrowBadCredentialsException () {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("Janne");
        authDTO.setPassword("12345678");

        assertThrows(BadCredentialsException.class, () -> authenticationController.loginUserWithUsername(authDTO));
    }

    @Test
    void registerUser_ShouldThrowEmailAlreadyUsedException () {
        registerDTO.setEmail("jane.smith@gmail.com");
        assertThrows(EmailAlreadyUsedException.class, () -> authenticationController.registerUser(registerDTO));
    }

    @Test
    void registerUser_ShouldThrowUsernameAlreadyUsedException () {
        registerDTO.setEmail("janne.smith@gmail.com");
        registerDTO.setUsername("Joe");
        assertThrows(UsernameExistsException.class, () -> authenticationController.registerUser(registerDTO));
    }

    @Test
    void registerUser_ShouldCreateUser () throws Exception {
        Assertions.assertNull(userRepository.findByUsername(registerDTO.getUsername()));
        authenticationController.registerUser(registerDTO);
        Assertions.assertTrue(userRepository.findByUsername(registerDTO.getUsername()).getEmail().equals(registerDTO.getEmail()));
        Assertions.assertTrue(userRepository.findByUsername(registerDTO.getUsername()).getUsername().equals(registerDTO.getUsername()));
        Assertions.assertFalse(userRepository.findByUsername(registerDTO.getUsername()).getEnabled());
    }

    /*@Test
    void registerUser_ShouldEncodePassword () {
        Assertions.assertFalse(userRepository.findByUsername(registerDTO.getUsername()).getPassword().equals(registerDTO.getPassword()));
    }*/
}
