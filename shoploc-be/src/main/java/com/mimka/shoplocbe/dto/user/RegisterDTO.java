package com.mimka.shoplocbe.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Lastname cannot be empty")
    private String lastname;

    @NotBlank(message = "Firstname cannot be empty")
    private String firstname;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String confirmedPassword;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "\\d", message = "Phone number must contain only digits")
    @Size(min = 10, max = 10, message = "Phone number must contain 10 characters")
    private String phoneNumber;
}