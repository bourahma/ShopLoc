package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.UserNotEnabledException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getUsers ();

    public User getUserByEmail (String email) throws UserNotEnabledException;

    public User getUserByUsername (String username) throws UserNotEnabledException;

    public User createUser (RegisterDTO registerDTO) throws EmailAlreadyUsedException, UserNotEnabledException, MessagingException;

    public User createOrga (UserDTO userDTO);
}
