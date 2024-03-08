package com.mimka.shoplocbe.dto;

import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoUtil {

    private ModelMapper modelMapper;

    @Autowired
    public DtoUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Customer utils :
    public Customer toCustomer (CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
    public CustomerDTO toCustomerDTO(Customer customer) {
        modelMapper.typeMap(Customer.class, CustomerDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(CustomerDTO::setConfirmedPassword);
                    mapper.skip(CustomerDTO::setPassword);
                });

        return modelMapper.map(customer, CustomerDTO.class);
    }

    // Merchant utils :
    public Merchant toMerchant (MerchantDTO merchantDTO) {
        return modelMapper.map(merchantDTO, Merchant.class);
    }
    public MerchantDTO toMerchantDTO(Merchant merchant) {
        modelMapper.typeMap(Merchant.class, MerchantDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(MerchantDTO::setConfirmedPassword);
                    mapper.skip(MerchantDTO::setPassword);
                    mapper.map(src -> src.getCommerce().getCommerceId(), MerchantDTO::setCommerceId);
                });

        return modelMapper.map(merchant, MerchantDTO.class);
    }

}
