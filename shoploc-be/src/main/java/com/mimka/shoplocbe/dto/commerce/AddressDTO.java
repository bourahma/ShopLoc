package com.mimka.shoplocbe.dto.commerce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String street;

    private String postalCode;

    private String city;

    private double latitude;

    private double longitude;
}
