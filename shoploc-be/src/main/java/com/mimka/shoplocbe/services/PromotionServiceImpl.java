package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Promotion promotion = this.productDTOUtil.toPromotion(promotionDTO);
        promotion.setProduct(product);
        promotion.setCommerce(product.getCommerce());
        promotion.setPromotionType(PromotionType.DISCOUNT.name());

        return this.promotionRepository.save(promotion);
    }

    @Override
    public Promotion createOfferPromotion(PromotionDTO promotionDTO, Product product) {
        Promotion promotion = this.productDTOUtil.toPromotion(promotionDTO);
        promotion.setProduct(product);
        promotion.setCommerce(product.getCommerce());
        promotion.setPromotionType(PromotionType.OFFER.name());

        return this.promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getCommercePromotions(Commerce commerce) {
        return this.promotionRepository.findAllByCommerce(commerce);
    }

    @Override
    public Promotion getPromotion(Long offerPromotionId) {
        return this.promotionRepository.findById(offerPromotionId).get();
    }
}