package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@EnableMethodSecurity(prePostEnabled = true)
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping(path = "/users")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public List<User> getUsers () {
        System.out.println("toto");return userServiceImpl.getUsers();
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping(path = "/user")
    public String helloUser () {
        return "Hello User";
    }

    @GetMapping(path = "/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String helloAdmin () {
        return "Hello Admin";
    }
}