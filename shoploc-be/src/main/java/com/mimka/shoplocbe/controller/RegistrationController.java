package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.service.RegistrationServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @PostMapping( "/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser (@RequestBody UserDTO userDTO) throws MessagingException, EmailAlreadyUsedException {
        this.registrationServiceImpl.register(userDTO);
    }

    @GetMapping("/verify/{uuid}")
    public void verifyUserRegistration (@PathVariable String uuid) {
        this.registrationServiceImpl.verify(uuid);
    }

}
