package com.mimka.shoplocbe.dto.commerce;

import com.mimka.shoplocbe.entities.Address;
import com.mimka.shoplocbe.entities.Commerce;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommerceDTOUtil {

    private ModelMapper modelMapper;

    @Autowired
    CommerceDTOUtil (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommerceDTO toCommerceDTO (Commerce commerce) {
        AddressDTO addressDTO = this.toAddressDTO(commerce.getAddress());
        CommerceDTO commerceDTO = modelMapper.map(commerce, CommerceDTO.class);
        commerceDTO.setCommerceId(commerce.getCommerceId());
        commerceDTO.setAddressDTO(addressDTO);

        return commerceDTO;
    }

    public Commerce toCommerce (CommerceDTO commerceDTO) {
        return modelMapper.map(commerceDTO, Commerce.class);
    }

    public Address toAddress (AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    public AddressDTO toAddressDTO (Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
}
