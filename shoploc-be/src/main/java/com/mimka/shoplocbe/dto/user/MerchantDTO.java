package com.mimka.shoplocbe.dto.user;

import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MerchantDTO {

    private String username;

    private String lastname;

    private String firstname;

    private String password;

    private String confirmedPassword;

    private String email;

    private String phoneNumber;

    private LocalDate subscriptionDate;

    private Commerce commerce;

    private  long commerceId;

    private Role role;
}
