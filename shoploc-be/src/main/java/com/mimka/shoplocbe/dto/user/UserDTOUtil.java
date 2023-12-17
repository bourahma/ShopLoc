package com.mimka.shoplocbe.dto.user;

import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserDTOUtil {

    private ModelMapper modelMapper;


    private String differentP = "Les mots de passe sont différents.";

    @Autowired
    public UserDTOUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toUser (RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }

    public boolean checkPasswords (RegisterDTO registerDTO) throws RegistrationException {
        if (!registerDTO.getConfirmedPassword().equals(registerDTO.getPassword())) {
            throw new RegistrationException(differentP);
        }
        return true;
    }

}
