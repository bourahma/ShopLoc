package com.mimka.shoplocbe.controller;


import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping(path = "/users")
    public List<User> getUsers () {
        return userServiceImpl.getUsers();
    }

    @GetMapping(path = "/user")
    public String helloUser () {
        return "Hello User";
    }

    @GetMapping(path = "/admin")
    public String helloAdmin () {
        return "Hello Admin";
    }
}