package com.mimka.shoplocbe.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class AuthDTO {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide.")
    private String username;

    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 20, message = "Le mot de passe doit contenir entre 8 et 20 caractères.")
    private String password;

}
