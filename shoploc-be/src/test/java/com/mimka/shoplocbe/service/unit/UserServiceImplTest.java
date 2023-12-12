package com.mimka.shoplocbe.service.unit;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repository.RoleRepository;
import com.mimka.shoplocbe.repository.UserRepository;
import com.mimka.shoplocbe.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDTOUtil userDTOUtil;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    // Methods should call the repository.
    @Test
    void getUserByEmail_shouldCallTheUserRepository() {
        when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(new User());
        userServiceImpl.getUserByEmail("john.doe@gmail.com");
        verify(userRepository).findByEmail("john.doe@gmail.com");
    }

    @Test
    void getUserByUsername_shouldCallTheUserRepository() {
        when(userRepository.findByUsername("Joe")).thenReturn(new User());
        userServiceImpl.getUserByUsername("Joe");
        verify(userRepository).findByUsername("Joe");
    }

    @Test
    void emailAndUsernameNotUsedYet_ShouldCallTheUserRepository () throws RegistrationException {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("john.doe@gmail.com");
        registerDTO.setUsername("Joe");

        userServiceImpl.emailAndUsernameNotUsedYet(registerDTO);

        verify(userRepository).findByEmail("john.doe@gmail.com");
        verify(userRepository).findByUsername("Joe");
    }

    @Test
    void emailAndUsernameNotUsedYet_ShouldReturnTrue () throws RegistrationException {
        RegisterDTO registerDTO = new RegisterDTO();

        when(userRepository.findByUsername("Joe")).thenReturn(null);
        when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(null);

        Assertions.assertThat(userServiceImpl.emailAndUsernameNotUsedYet(registerDTO)).isTrue();
    }

    @Test
    void emailAndUsernameNotUsedYet_ShouldThrowEmailAlreadyUsedException () {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("john.doe@gmail.com");

        when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(new User());

        assertThrows(RegistrationException.class, () -> userServiceImpl.emailAndUsernameNotUsedYet(registerDTO));
    }

    @Test
    void emailAndUsernameNotUsedYet_ShouldThrowUsernameExistsException () {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Joe");

        when(userRepository.findByUsername("Joe")).thenReturn(new User());

        assertThrows(RegistrationException.class, () -> userServiceImpl.emailAndUsernameNotUsedYet(registerDTO));
    }

    @Test
    void addUser_ShouldCallTheUserRepository() throws RegistrationException {
        RegisterDTO registerDTO = new RegisterDTO();
        User user = new User();
        when(userDTOUtil.toUser(registerDTO)).thenReturn(user);
        userServiceImpl.addUser(registerDTO, new HashSet<>());
        verify(userRepository).save(user);
    }

    @Test
    void userRoles_ShouldCallTheUserRepository() {
        userServiceImpl.userRoles();
        verify(roleRepository).findByRoleId(1L);
    }

    @Test
    void orgaRoles_ShouldCallTheUserRepository() {
        userServiceImpl.orgaRoles();
        verify(roleRepository).findByRoleId(2L);
    }

    @Test
    void amdinRoles_ShouldCallTheUserRepository() {
        userServiceImpl.amdinRoles();
        verify(roleRepository).findAll();
    }
}