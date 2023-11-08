package com.mimka.shoplocbe.dto.user;

import com.mimka.shoplocbe.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOUtil {

    @Autowired
    private ModelMapper modelMapper;
    public UserDTO toUserDTO (User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toUser (UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
