package com.mimka.shoplocbe.dto;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.RegistrationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoUtil {

    private ModelMapper modelMapper;

    private String differentP = "Les mots de passe sont différents.";

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
                });

        return modelMapper.map(merchant, MerchantDTO.class);
    }

    // Administrator utils :
    public Administrator toAdministrator (AdministratorDTO administratorDTO) {
        return modelMapper.map(administratorDTO, Administrator.class);
    }
    public AdministratorDTO toAdministratorDTO (Administrator administrator) {
        modelMapper.typeMap(Administrator.class, AdministratorDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(AdministratorDTO::setConfirmedPassword);
                    mapper.skip(AdministratorDTO::setPassword);
                });

        return modelMapper.map(administrator, AdministratorDTO.class);
    }

    public User toUser (RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }

    public boolean checkPasswords (String confirmedPassword, String password) throws RegistrationException {
        if (!confirmedPassword.equals(password)) {
            throw new RegistrationException(differentP);
        }
        return true;
    }

}
