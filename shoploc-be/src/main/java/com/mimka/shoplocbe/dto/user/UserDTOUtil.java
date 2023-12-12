package com.mimka.shoplocbe.dto.user;

import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserDTOUtil {

    @Autowired
    private ModelMapper modelMapper;

    @Value("${register.message.passwrods.different}")
    private String differentPasswords;

    public UserDTO toUserDTO (User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toUser (UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public User toUser (RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }

    public boolean checkPasswords (RegisterDTO registerDTO) throws RegistrationException {
        if (!registerDTO.getConfirmedPassword().equals(registerDTO.getPassword())) {
            throw new RegistrationException(differentPasswords);
        }
        return true;
    }

}
