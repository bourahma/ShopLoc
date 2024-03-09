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

    private final PromotionRepository<OfferPromotion> offerPromotionRepository;
    private final PromotionRepository<Promotion> promotionRepository;
    private final PromotionRepository<DiscountPromotion> discountPromotionRepository;
    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public PromotionServiceImpl(PromotionRepository<OfferPromotion> offerPromotionRepository, PromotionRepository<Promotion> promotionRepository1, PromotionRepository<DiscountPromotion> discountPromotionRepository, ProductDTOUtil productDTOUtil) {
        this.offerPromotionRepository = offerPromotionRepository;
        this.promotionRepository = promotionRepository1;
        this.discountPromotionRepository = discountPromotionRepository;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public Promotion createDiscountPromotion(PromotionDTO promotionDTO, Product product) {
        DiscountPromotion discountPromotion = this.productDTOUtil.toDiscountPromotion(promotionDTO);
        discountPromotion.setProduct(product);
        discountPromotion.setCommerce(product.getCommerce());

        return this.discountPromotionRepository.save(discountPromotion);
    }

    @Override
    public Promotion createOfferPromotion(PromotionDTO promotionDTO, Product product) {
        OfferPromotion offerPromotion = this.productDTOUtil.toOfferPromotion(promotionDTO);
        offerPromotion.setProduct(product);
        offerPromotion.setCommerce(product.getCommerce());

        return this.offerPromotionRepository.save(offerPromotion);
    }

    @Override
    public List<Promotion> getCommercePromotions(Commerce commerce) {
        return this.promotionRepository.findAllByCommerce(commerce);
    }
}
