package com.mimka.shoplocbe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;

    private String postalCode;

    private String city;

    private double latitude;

    private double longitude;

}
