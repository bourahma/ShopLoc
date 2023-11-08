package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.UserNotEnabledException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public List<User> getUsers ();

    public User getUserByEmail (String email) throws UserNotEnabledException;

    public User getUserByUsername (String username) throws UserNotEnabledException;

    public User getUserByVerificationCode (String uuid);

    public User createUser (UserDTO userDTO) throws EmailAlreadyUsedException, UserNotEnabledException, MessagingException;

    public User createUserOrga (UserDTO userDTO);
}
