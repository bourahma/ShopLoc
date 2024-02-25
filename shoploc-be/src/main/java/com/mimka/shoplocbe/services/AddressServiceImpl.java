package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.api.map.MapAPI;
import com.mimka.shoplocbe.dto.commerce.AddressDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.entities.Address;
import com.mimka.shoplocbe.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final MapAPI mapAPI;

    private final CommerceDTOUtil commerceDTOUtil;

    public AddressServiceImpl(AddressRepository addressRepository, MapAPI mapAPI, CommerceDTOUtil commerceDTOUtil) {
        this.addressRepository = addressRepository;
        this.mapAPI = mapAPI;
        this.commerceDTOUtil = commerceDTOUtil;
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        addressDTO = this.mapAPI.getCoordinates(addressDTO);
        Address address = this.commerceDTOUtil.toAddress(addressDTO);

        this.addressRepository.save(address);

        return address;
    }
}
