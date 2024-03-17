package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.vfp.VfpDTO;
import com.mimka.shoplocbe.dto.vfp.VfpDTOUtil;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.FidelityCard;
import com.mimka.shoplocbe.entities.Token;
import com.mimka.shoplocbe.entities.VfpHistory;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.exception.RegistrationTokenInvalidException;
import com.mimka.shoplocbe.services.*;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class CustomerFacade {

    private final CustomerService customerService;

    private final FidelityCardService fidelityCardService;

    private final MailServiceImpl mailServiceImpl;

    private final VfpHistoryService vfpHistoryService;

    private final TokenService tokenService;

    private final DtoUtil dtoUtil;

    private final VfpDTOUtil vfpDTOUtil;

    @Autowired
    public CustomerFacade(CustomerService createCustomer, FidelityCardService fidelityCardService, MailServiceImpl mailServiceImpl, VfpHistoryService vfpHistoryService, TokenService tokenService, DtoUtil dtoUtil, VfpDTOUtil vfpDTOUtil) {
        this.customerService = createCustomer;
        this.fidelityCardService = fidelityCardService;
        this.mailServiceImpl = mailServiceImpl;
        this.vfpHistoryService = vfpHistoryService;
        this.tokenService = tokenService;
        this.dtoUtil = dtoUtil;
        this.vfpDTOUtil = vfpDTOUtil;
    }


    public CustomerDTO registerCustomer(CustomerDTO customerDTO) throws RegistrationException {
        FidelityCard fidelityCard = this.fidelityCardService.createFidelityCard();
        Customer customer = this.customerService.createCustomer(customerDTO, fidelityCard);
        this.sendVerificationEmail(customer);

        return this.dtoUtil.toCustomerDTO(customer);
    }

    public void confirmCustomerRegistration(String uuid) throws RegistrationTokenInvalidException {
        Customer customer = this.customerService.enableCustomer(uuid);

        try {
            this.mailServiceImpl.triggerWelcomeEmail(customer);
        } catch (MessagingException e) {
            log.warn("Sending welcome e-mail : " + e.getMessage());
        }
    }

    public void sendVerificationEmail (Customer customer) {
        Token token = this.tokenService.createToken(customer);

        try {
            this.mailServiceImpl.triggerEmailVerification(customer, token.getUuid().toString());
        } catch (MessagingException e) {
            log.warn("Sending email verification error : " + e.getMessage());
        }
    }

    public VfpDTO customerVfpMembership (String customerUsername) {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);
        VfpHistory vfpHistory = this.vfpHistoryService.lastVFPStatusGranted(customer);

        return this.vfpDTOUtil.toVfpDTO(vfpHistory, customer);
    }

    public List<VfpDTO> customerVfpMembershipHistory(String customerUsername) {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);
        List<VfpHistory> customerVFPMembershipHistory = this.vfpHistoryService.customerVFPMembershipHistory(customer);

        return customerVFPMembershipHistory.stream()
                .map(vfpHistory -> vfpDTOUtil.toVfpDTO(vfpHistory, customer))
                .collect(Collectors.toList());
    }
}
