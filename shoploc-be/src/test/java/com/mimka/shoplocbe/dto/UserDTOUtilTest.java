package com.mimka.shoplocbe.dto;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDTOUtilTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserDTOUtil userDTOUtil;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void toUser_ShouldMapRegisterDTOToUser() {
        RegisterDTO registerDTO = new RegisterDTO();
        User expectedUser = new User();

        when(modelMapper.map(registerDTO, User.class)).thenReturn(expectedUser);

        User actualUser = userDTOUtil.toUser(registerDTO);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);

        verify(modelMapper).map(registerDTO, User.class);
    }

    @Test
    void checkPasswords_WhenPasswordsAreDifferent_ShouldThrowRegistrationException() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setPassword("password1");
        registerDTO.setConfirmedPassword("password2");

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            userDTOUtil.checkPasswords(registerDTO);
        });

        assertEquals("Les mots de passe sont différents.", exception.getMessage());
    }

    @Test
    void checkPasswords_WhenPasswordsAreSame_ShouldNotThrowException() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword("password");
        registerDTO.setConfirmedPassword("password");

        assertDoesNotThrow(() -> userDTOUtil.checkPasswords(registerDTO));
    }
}
