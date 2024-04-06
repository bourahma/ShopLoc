package com.mimka.shoplocbe.dto.benefit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenefitDTO {

    private Long benefitId;

    private Boolean benefitAvailable;

    private String imageUrl;

    @NotBlank(message = "Une description de l'avatange est requis.")
    private String description;

    @Pattern(regexp = "^(|^[A-Z]{2}-\\d{3}-[A-Z]{2})$", message = "Le numéro ne correspond pas à une immatriculation valide.")
    private String licensePlateNumber;
}