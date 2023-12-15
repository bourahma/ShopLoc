package com.mimka.shoplocbe.controller.unit;

import com.mimka.shoplocbe.controller.AuthenticationController;
import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.service.AuthenticationServiceImpl;
import com.mimka.shoplocbe.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationServiceImpl authenticationServiceImpl;

    @Mock
    private RegistrationServiceImpl registrationServiceImpl;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    // Controller methods should be annotated.
    @Test
    void AuthenticationControllerShouldBeAnnotated () {
        assertNotNull(AuthenticationController.class.getAnnotation(RestController.class));
    }

    @Test
    void loginUserWithUsername_ShouldBeAnnotated () throws NoSuchMethodException {
        var loginUserWithUsernameMethod = AuthenticationController.class.getDeclaredMethod("loginUserWithUsername", AuthDTO.class);
        var postMapping = loginUserWithUsernameMethod.getAnnotation(PostMapping.class);
        assertNotNull(loginUserWithUsernameMethod.getAnnotation(PostMapping.class));
        assertArrayEquals(new String[]{"/login"}, postMapping.value());
    }

    @Test
    void registerUser_ShouldBeAnnotated () throws NoSuchMethodException {
        var registerUserMethod = AuthenticationController.class.getDeclaredMethod("registerUser", RegisterDTO.class);
        var postMapping = registerUserMethod.getAnnotation(PostMapping.class);
        assertNotNull(registerUserMethod.getAnnotation(PostMapping.class));
        assertArrayEquals(new String[]{"/register"}, postMapping.value());
    }

    // Controller methods should call the service.
    @Test
    void loginUserWithUsername_shouldCallTheService() {
        var authDTO = new AuthDTO();
        authDTO.setUsername("user");
        authDTO.setPassword("password");

        authenticationController.loginUserWithUsername(authDTO);
        verify(authenticationServiceImpl).loginUserWithUsername(authDTO.getUsername(), authDTO.getPassword());
    }

    @Test
    void registerUser_shouldCallTheService() throws Exception {
        var registerDTO = new RegisterDTO();
        registerDTO.setEmail("email");
        registerDTO.setUsername("username");
        registerDTO.setPassword("password");
        registerDTO.setConfirmedPassword("password");
        registerDTO.setFirstname("firstname");
        registerDTO.setLastname("lastname");
        registerDTO.setPhoneNumber("00 11 22 33 44");
        registerDTO.setEmail("address@email.com");

        authenticationController.registerUser(registerDTO);
        verify(registrationServiceImpl).register(registerDTO);
    }
}
