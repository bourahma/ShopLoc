package com.mimka.shoplocbe.service.unit;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.repository.UserRepository;
import com.mimka.shoplocbe.service.EmailServiceImpl;
import com.mimka.shoplocbe.service.RegistrationServiceImpl;
import com.mimka.shoplocbe.service.RegistrationTokenServiceImpl;
import com.mimka.shoplocbe.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;

public class RegistrationServiceImplTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegistrationTokenServiceImpl registrationTokenServiceImpl;

    @Mock
    private EmailServiceImpl emailServiceImpl;

    @Mock
    private UserDTOUtil userDTOUtil;

    @InjectMocks
    private RegistrationServiceImpl registrationServiceImpl;
    private static RegisterDTO registerDTO;
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        registerDTO = new RegisterDTO();
        registerDTO.setUsername("username");
        registerDTO.setPassword("password");
        registerDTO.setConfirmedPassword("password");
        registerDTO.setFirstname("firstname");
        registerDTO.setLastname("lastname");
        registerDTO.setPhoneNumber("00 11 22 33 44");
        registerDTO.setEmail("address@email.com");
    }

    /*@Test
    void getUserByEmail_shouldCallTheUserRepository() {
        when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(new User());
        userServiceImpl.getUserByEmail("john.doe@gmail.com");
        verify(userRepository).findByEmail("john.doe@gmail.com");
    }*/

}
