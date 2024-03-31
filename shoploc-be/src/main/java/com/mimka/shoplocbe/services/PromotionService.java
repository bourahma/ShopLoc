package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.*;

import java.util.List;

public interface PromotionService {

    Promotion createDiscountPromotion(PromotionDTO promotionDTO, Commerce commerce);

    Promotion createOfferPromotion(PromotionDTO promotionDTO, Commerce commerce);

    List<Promotion> getCommercePromotions(Commerce commerce);

    Promotion getPromotion(Long offerPromotionId);
}
