package com.mimka.shoplocbe.api.map;

import com.mimka.shoplocbe.dto.commerce.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MapAPI {

    private static final String API_URL = "https://api-adresse.data.gouv.fr/search/?q={address}";

    private final RestTemplate restTemplate;

    @Autowired
    public MapAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AddressDTO getCoordinates(AddressDTO addressDTO) {
        String address = String.format("%d %s %s", addressDTO.getPostalCode(), addressDTO.getStreet(), addressDTO.getCity());

        AddressResponse response = restTemplate.getForObject(API_URL, AddressResponse.class, address);

        if (response != null && response.getFeatures() != null && !response.getFeatures().isEmpty()) {
            Feature feature = response.getFeatures().get(0);
            Geometry geometry = feature.getGeometry();

            addressDTO.setLatitude(geometry.getCoordinates()[1]);
            addressDTO.setLongitude(geometry.getCoordinates()[0]);

            return addressDTO;
        }
        return null;
    }
}
