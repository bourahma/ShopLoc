package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.exception.ProductPromotionException;

import java.util.List;

public interface PromotionFacade {

    PromotionDTO createOfferPromotion (PromotionDTO promotionDTO) throws ProductException, ProductPromotionException, CommerceNotFoundException;

    PromotionDTO createDiscountPromotion (PromotionDTO promotionDTO) throws ProductException, ProductPromotionException, CommerceNotFoundException;

    List<PromotionDTO> getCommercePromotions (Long commerceId) throws CommerceNotFoundException;
}
