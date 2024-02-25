package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.services.MailServiceImpl;
import com.mimka.shoplocbe.services.MerchantService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MerchantFacade {

    private final MerchantService merchantService;

    private final MailServiceImpl mailServiceImpl;

    public MerchantFacade(MerchantService merchantService, MailServiceImpl mailServiceImpl) {
        this.merchantService = merchantService;
        this.mailServiceImpl = mailServiceImpl;
    }

    public Merchant createMerchant(MerchantDTO merchantDTO) throws RegistrationException {
        Merchant merchant = this.merchantService.createMerchant(merchantDTO); // TODO : handle error on creating merchant.

        try {
            this.mailServiceImpl.triggerCredentialsEmail(merchantDTO);
        } catch (MessagingException e) {
            log.warn("Sending merchant email error : " + e.getMessage());
        }
        return merchant;
    }
}
