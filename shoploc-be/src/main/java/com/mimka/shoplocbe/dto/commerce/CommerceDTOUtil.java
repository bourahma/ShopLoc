package com.mimka.shoplocbe.dto.commerce;

import com.mimka.shoplocbe.entity.Commerce;
import com.mimka.shoplocbe.entity.Product;
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
    public Product toCommerce (CommerceDTO commerceDTO) {
        return modelMapper.map(commerceDTO, Product.class);
    }

    public CommerceDTO toCommerceDTO (Commerce commerce) {
        return modelMapper.map(commerce, CommerceDTO.class);
    }
}
