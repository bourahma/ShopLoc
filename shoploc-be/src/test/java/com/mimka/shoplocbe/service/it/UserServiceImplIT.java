package com.mimka.shoplocbe.service.it;

import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.repository.RoleRepository;
import com.mimka.shoplocbe.repository.UserRepository;
import com.mimka.shoplocbe.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceImplIT {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDTOUtil userDTOUtil;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void UserServiceImpl_ShouldBeAnnotated () {
        assertNotNull(UserServiceImpl.class.getAnnotation(Service.class));
    }

}
