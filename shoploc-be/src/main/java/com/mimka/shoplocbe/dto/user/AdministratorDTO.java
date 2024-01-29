package com.mimka.shoplocbe.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class AdministratorDTO {

    private String username;

    private String lastname;

    private String firstname;

    private String password;

    private String confirmedPassword;

    private String email;

    private String phoneNumber;

    private LocalDate subscriptionDate;

}
