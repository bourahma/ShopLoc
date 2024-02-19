package com.mimka.shoplocbe.dto.commerce;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CommerceDTO {

    private Long commerceId;

    @NotBlank(message = "Le nom du commerce est requis")
    private String commerceName;

    @NotNull(message = "L'heure d'ouverture est requise")
    private LocalTime openingHour;

    @NotNull(message = "L'heure de fermeture est requise")
    private LocalTime closingHour;

    @NotBlank(message = "L'URL de l'image est requise")
    private String imageUrl;

    @NotNull(message = "Les informations d'adresse sont requises")
    private AddressDTO addressDTO;

}
