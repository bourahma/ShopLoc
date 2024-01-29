package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.*;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserServiceImpl userServiceImpl;

    private PasswordEncoder passwordEncoder;

    private UserDTOUtil userDTOUtil;

    private final DtoUtil dtoUtil;

    @Autowired
    public RegistrationServiceImpl (UserServiceImpl userServiceImpl, UserDTOUtil userDTOUtil, PasswordEncoder passwordEncoder, DtoUtil dtoUtil) {
        this.userServiceImpl = userServiceImpl;
        this.userDTOUtil = userDTOUtil;
        this.passwordEncoder = passwordEncoder;
        this.dtoUtil = dtoUtil;
    }

    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) throws RegistrationException {
        // Check passwords are valid.
        if (this.dtoUtil.checkPasswords(customerDTO.getConfirmedPassword(), customerDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(customerDTO.getPassword());
            customerDTO.setPassword(encodedPassword);
            customerDTO.setConfirmedPassword(encodedPassword);
        }
        //Create customer.
        return this.dtoUtil.toCustomerDTO(this.userServiceImpl.createCustomer(customerDTO));
    }

    @Override
    public MerchantDTO registerMerchant(MerchantDTO merchantDTO) throws RegistrationException {
        String password = merchantDTO.getPassword();

        // Check passwords are valid.
        if (this.dtoUtil.checkPasswords(merchantDTO.getConfirmedPassword(), merchantDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(merchantDTO.getPassword());
            merchantDTO.setPassword(encodedPassword);
            merchantDTO.setConfirmedPassword(encodedPassword);
        }
        //Create merchant.
        Merchant merchant = this.userServiceImpl.createMerchant(merchantDTO);
        merchantDTO.setPassword(password);
        this.userServiceImpl.sendCredentialsEmail(merchantDTO);

        return this.dtoUtil.toMerchantDTO(merchant);
    }
    @Override
    public AdministratorDTO registerAdministrator(AdministratorDTO administratorDTO) throws RegistrationException {
        // Check passwords are valid.
        if (this.dtoUtil.checkPasswords(administratorDTO.getConfirmedPassword(), administratorDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(administratorDTO.getPassword());
            administratorDTO.setPassword(encodedPassword);
            administratorDTO.setConfirmedPassword(encodedPassword);
        }
        //Create customer.
        return this.dtoUtil.toAdministratorDTO(this.userServiceImpl.createAdministrator(administratorDTO));
    }

    @Override
    public void confirmRegistration(String uuid) {
        this.userServiceImpl.enableUser(uuid);
    }
}
