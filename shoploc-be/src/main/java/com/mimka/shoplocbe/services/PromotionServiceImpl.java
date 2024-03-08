package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ProductDTOUtil productDTOUtil) {
        this.promotionRepository = promotionRepository;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public Promotion createDiscountPromotion(PromotionDTO promotionDTO, Product product) {
        DiscountPromotion discountPromotion = this.productDTOUtil.toDiscountPromotion(promotionDTO);
        discountPromotion.setProduct(product);
        discountPromotion.setCommerce(product.getCommerce());

        return (Promotion) this.promotionRepository.save(discountPromotion);
    }

    @Override
    public Promotion createOfferPromotion(PromotionDTO promotionDTO, Product product) {
        OfferPromotion offerPromotion = this.productDTOUtil.toOfferPromotion(promotionDTO);
        offerPromotion.setProduct(product);
        offerPromotion.setCommerce(product.getCommerce());

        return (Promotion) this.promotionRepository.save(offerPromotion);
    }
}
