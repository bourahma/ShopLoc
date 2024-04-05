package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Promotion;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.services.CommerceService;
import com.mimka.shoplocbe.services.CustomerService;
import com.mimka.shoplocbe.services.MailServiceImpl;
import com.mimka.shoplocbe.services.PromotionService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PromotionFacadeImpl implements PromotionFacade {

    private final PromotionService promotionService;

    private final CommerceService commerceService;

    private final CustomerService customerService;

    private final MailServiceImpl mailService;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public PromotionFacadeImpl(PromotionService promotionService, CommerceService commerceService, CustomerService customerService, MailServiceImpl mailService, ProductDTOUtil productDTOUtil) {
        this.promotionService = promotionService;
        this.commerceService = commerceService;
        this.customerService = customerService;
        this.mailService = mailService;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public PromotionDTO createOfferPromotion(PromotionDTO promotionDTO, Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        Promotion promotion = this.promotionService.createOfferPromotion(promotionDTO, commerce);

        return this.productDTOUtil.toPromotionDTO(promotion);
    }

    @Override
    public PromotionDTO createDiscountPromotion(PromotionDTO promotionDTO, Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        Promotion promotion = this.promotionService.createDiscountPromotion(promotionDTO, commerce);

        return this.productDTOUtil.toPromotionDTO(promotion);
    }

    @Override
    public PromotionDTO getPromotion(Long promotionId) {
        return this.productDTOUtil.toPromotionDTO(this.promotionService.getPromotion(promotionId));
    }

    @Override
    public List<PromotionDTO> getCommercePromotions(Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        List<Promotion> promotions = this.promotionService.getCommercePromotions(commerce);

        return promotions.stream()
                .map(productDTOUtil::toPromotionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromotionDTO> getPromotions() {
        List<Promotion> promotions = this.promotionService.getPromotions();

        return promotions.stream()
                .filter(promotion -> !promotion.isSent())
                .map(productDTOUtil::toPromotionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PromotionDTO launchPromotion(Long promotionId) {
        Promotion promotion = this.promotionService.getPromotion(promotionId);
        List<Customer> customers = this.customerService.getCustomers();
        Commerce commerce = promotion.getCommerce();

        try {
            this.mailService.triggerPromotionToCustomers(promotion, commerce, customers);
        } catch (MessagingException e) {
            log.warn("Sending promotion error : " + e.getMessage());
        }

        return null;
    }
}

