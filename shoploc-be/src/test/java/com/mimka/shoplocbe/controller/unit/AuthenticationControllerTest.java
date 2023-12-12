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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class AuthenticationControllerTest {

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

    @Test
    void verifyUserRegistration_ShouldBeAnnotated () throws NoSuchMethodException {
        var verifyUserRegistrationMethod = AuthenticationController.class.getDeclaredMethod("verifyUserRegistration", String.class);
        var getMapping = verifyUserRegistrationMethod.getAnnotation(GetMapping.class);
        assertNotNull(verifyUserRegistrationMethod.getAnnotation(GetMapping.class));
        assertArrayEquals(new String[]{"/verify/{uuid}"}, getMapping.value());
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

    @Test
    void verifyUserRegistration_shouldCallTheService() {
        var uuid = "ed1994ba-0f22-4ef2-8976-9440b19675e1";
        authenticationController.verifyUserRegistration(uuid);
        verify(registrationServiceImpl).verify(uuid);
    }
}
