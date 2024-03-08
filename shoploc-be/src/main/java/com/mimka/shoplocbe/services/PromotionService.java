package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.Promotion;

public interface PromotionService {

    Promotion createDiscountPromotion (PromotionDTO promotionDTO, Product product);

    Promotion createOfferPromotion (PromotionDTO promotionDTO, Product product);
}
