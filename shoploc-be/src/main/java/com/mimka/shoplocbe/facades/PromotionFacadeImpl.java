package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.Promotion;
import com.mimka.shoplocbe.entities.PromotionType;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.repositories.PromotionRepository;
import com.mimka.shoplocbe.services.CommerceService;
import com.mimka.shoplocbe.services.ProductService;
import com.mimka.shoplocbe.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromotionFacadeImpl implements PromotionFacade {

    private final PromotionService promotionService;

    private final CommerceService commerceService;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public PromotionFacadeImpl(PromotionService promotionService, CommerceService commerceService, ProductDTOUtil productDTOUtil) {
        this.promotionService = promotionService;
        this.commerceService = commerceService;
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

    private List<PromotionDTO> getCommercePromotions(Long commerceId, String promotionType) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        List<Promotion> promotions = this.promotionService.getCommercePromotions(commerce);

        return promotions.stream()
                .filter(promotion -> promotion.getPromotionType().equals(promotionType))
                .map(productDTOUtil::toPromotionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromotionDTO> getCommerceOfferPromotions(Long commerceId) throws CommerceNotFoundException {
        return this.getCommercePromotions(commerceId, PromotionType.OFFER.name());
    }

    @Override
    public List<PromotionDTO> getCommerceDiscountPromotions(Long commerceId) throws CommerceNotFoundException {
        return this.getCommercePromotions(commerceId, PromotionType.DISCOUNT.name());
    }
}

