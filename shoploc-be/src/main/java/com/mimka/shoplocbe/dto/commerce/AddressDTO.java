package com.mimka.shoplocbe.dto.commerce;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @NotBlank(message = "Le nom de la rue est requis")
    private String street;

    private int postalCode;

    @NotBlank(message = "La ville est requise")
    private String city;

    private double latitude;

    private double longitude;
}
