package com.mimka.shoplocbe.repository;

import com.mimka.shoplocbe.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll_ShouldReturnAllUsers() {
        Assertions.assertEquals(3, userRepository.findAll().size());
    }

    @Test
    void findById_ShouldReturnUser() {
        Assertions.assertEquals("john.doe@gmail.com", userRepository.findByEmail("john.doe@gmail.com").getEmail());
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        Assertions.assertEquals("Jane", userRepository.findByUsername("Jane").getUsername());
    }

    @Test
    void save_ShouldSaveUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setPhoneNumber("00 11 22 33 44");
        user.setEnabled(false);
        user.setEmail("address@email.com");

        userRepository.save(user);
        Assertions.assertEquals(4, userRepository.findAll().size());

    }
}
