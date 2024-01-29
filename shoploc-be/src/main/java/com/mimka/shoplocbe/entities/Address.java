package com.mimka.shoplocbe.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String street;

    private String postalCode;

    private String city;

    private double latitude;

    private double longitude;
}
