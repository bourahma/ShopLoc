package com.mimka.shoplocbe.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String lastname;

    private String firstname;

    private String password;

    private String confirmedPassword;

    private String email;

    private String phoneNumber;

}
