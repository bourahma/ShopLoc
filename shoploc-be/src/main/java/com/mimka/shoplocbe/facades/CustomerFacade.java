package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.FidelityCard;
import com.mimka.shoplocbe.entities.Token;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.exception.RegistrationTokenInvalidException;
import com.mimka.shoplocbe.services.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerFacade {

    private final CustomerService customerService;

    private final FidelityCardService fidelityCardService;

    private final MailServiceImpl mailServiceImpl;

    private final TokenService tokenService;

    @Autowired
    public CustomerFacade(CustomerService createCustomer, FidelityCardService fidelityCardService, MailServiceImpl mailServiceImpl, TokenService tokenService) {
        this.customerService = createCustomer;
        this.fidelityCardService = fidelityCardService;
        this.mailServiceImpl = mailServiceImpl;
        this.tokenService = tokenService;
    }


    public Customer registerCustomer(CustomerDTO customerDTO) throws RegistrationException {
        FidelityCard fidelityCard = this.fidelityCardService.createFidelityCard();
        Customer customer = this.customerService.createCustomer(customerDTO, fidelityCard);
        this.sendVerificationEmail(customer);

        return customer;
    }

    public void confirmCustomerRegistration(String uuid) throws RegistrationTokenInvalidException {
        Customer customer = this.customerService.enableCustomer(uuid);

        try {
            this.mailServiceImpl.triggerWelcomeEmail(customer);
        } catch (MessagingException e) {
            System.out.println("Sending welcome e-mail : " + e.getMessage());
        }
    }

    public void sendVerificationEmail (Customer customer) {
        Token token = this.tokenService.createToken(customer);

        try {
            this.mailServiceImpl.triggerEmailVerification(customer, token.getUuid().toString());
        } catch (MessagingException e) {
            System.out.println("Sending email verification error : " + e.getMessage());
        }
    }
}
