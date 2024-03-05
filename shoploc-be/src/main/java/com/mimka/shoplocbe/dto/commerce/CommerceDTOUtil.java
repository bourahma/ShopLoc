package com.mimka.shoplocbe.dto.commerce;

import com.mimka.shoplocbe.entities.Address;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
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
        CommerceDTO commerceDTO = modelMapper.map(commerce, CommerceDTO.class);
        AddressDTO addressDTO = this.toAddressDTO(commerce.getAddress());
        CommerceTypeDTO commerceTypeDTO = this.toCommerceTypeDTO(commerce.getCommerceType());
        commerceDTO.setCommerceId(commerce.getCommerceId());
        commerceDTO.setAddressDTO(addressDTO);
        commerceDTO.setCommerceType(commerceTypeDTO);

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

    public CommerceType toCommerceType (CommerceTypeDTO commerceTypeDTO) {return  modelMapper.map(commerceTypeDTO, CommerceType.class);}
    public CommerceTypeDTO toCommerceTypeDTO (CommerceType commerceType) {return  modelMapper.map(commerceType, CommerceTypeDTO.class);}
}
