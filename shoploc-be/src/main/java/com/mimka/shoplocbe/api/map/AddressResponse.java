package com.mimka.shoplocbe.api.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponse {

    private List<Feature> features;

}
