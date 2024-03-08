package com.mimka.shoplocbe.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerDTO {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide.")
    private String username;

    @NotBlank(message = "Le nom de famille ne peut pas être vide.")
    private String lastname;

    @NotBlank(message = "Le prénom ne peut pas être vide.")
    private String firstname;

    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 20, message = "Le mot de passe doit contenir entre 8 et 20 caractères.")
    private String password;

    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 20, message = "Le mot de passe doit contenir entre 8 et 20 caractères.")
    private String confirmedPassword;

    @Email(message = "Adresse e-mail invalide.")
    private String email;

    @Pattern(regexp = "\\d+$", message = "Le numéro de téléphone doit contenir uniquement des chiffres.")
    @Size(min = 10, max = 10, message = "Le numéro de téléphone doit contenir 10 caractères.")
    private String phoneNumber;

    private LocalDate subscriptionDate;
}
