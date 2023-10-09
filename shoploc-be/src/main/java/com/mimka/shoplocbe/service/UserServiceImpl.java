package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}