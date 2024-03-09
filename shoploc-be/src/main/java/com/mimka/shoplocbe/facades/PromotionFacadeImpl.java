package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.Promotion;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.repositories.PromotionRepository;
import com.mimka.shoplocbe.services.CommerceService;
import com.mimka.shoplocbe.services.ProductService;
import com.mimka.shoplocbe.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionFacadeImpl implements PromotionFacade {

    private final PromotionService promotionService;

    private final ProductService productService;

    private final CommerceService commerceService;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public PromotionFacadeImpl(PromotionService promotionService, ProductService productService, CommerceService commerceService, ProductDTOUtil productDTOUtil) {
        this.promotionService = promotionService;
        this.productService = productService;
        this.commerceService = commerceService;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public PromotionDTO createOfferPromotion(PromotionDTO promotionDTO) throws CommerceNotFoundException, ProductException {
        Product product = this.productService.getProduct(promotionDTO.getProductId());
        Promotion promotion = this.promotionService.createOfferPromotion(promotionDTO, product);

        return this.productDTOUtil.toPromotionDTO(promotion);
    }

    @Override
    public PromotionDTO createDiscountPromotion(PromotionDTO promotionDTO) throws ProductException, CommerceNotFoundException {
        Product product = this.productService.getProduct(promotionDTO.getProductId());
        Promotion promotion = this.promotionService.createDiscountPromotion(promotionDTO, product);

        return this.productDTOUtil.toPromotionDTO(promotion);
    }

    @Override
    public List<PromotionDTO> getCommercePromotions(Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        List<Promotion> promotions = this.promotionService.getCommercePromotions(commerce);

        return promotions
                .stream()
                .map(productDTOUtil::toPromotionDTO).toList();
    }
}

