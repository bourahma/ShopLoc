package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.Promotion;

import java.util.List;

public interface PromotionService {

    Promotion createDiscountPromotion (PromotionDTO promotionDTO, Product product);

    Promotion createOfferPromotion (PromotionDTO promotionDTO, Product product);

    List<Promotion> getCommercePromotions (Commerce commerce);
}
