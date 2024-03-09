package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.services.CommerceService;
import com.mimka.shoplocbe.services.MailServiceImpl;
import com.mimka.shoplocbe.services.MerchantService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MerchantFacade {

    private final MerchantService merchantService;

    private final MailServiceImpl mailServiceImpl;

    private final CommerceService commerceService;

    private final DtoUtil dtoUtil;

    @Autowired
    public MerchantFacade(MerchantService merchantService, MailServiceImpl mailServiceImpl, CommerceService commerceService, DtoUtil dtoUtil) {
        this.merchantService = merchantService;
        this.mailServiceImpl = mailServiceImpl;
        this.commerceService = commerceService;
        this.dtoUtil = dtoUtil;
    }

    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws RegistrationException, CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(merchantDTO.getCommerceId());
        Merchant merchant = this.merchantService.createMerchant(merchantDTO, commerce);

        try {
            this.mailServiceImpl.triggerCredentialsEmail(merchantDTO);
        } catch (MessagingException e) {
            log.warn("Error occurred when sending merchant credentials : " + e.getMessage());
        }
        return this.dtoUtil.toMerchantDTO(merchant);
    }
}
