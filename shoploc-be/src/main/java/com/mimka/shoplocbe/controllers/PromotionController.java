package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.product.PromotionDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.exception.ProductPromotionException;
import com.mimka.shoplocbe.facades.PromotionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionFacade promotionFacade;

    @Autowired
    public PromotionController(PromotionFacade promotionFacade) {
        this.promotionFacade = promotionFacade;
    }

    @PostMapping("/offer/{commerceId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MERCHANT')")
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionDTO fireOfferPromotion (@RequestBody PromotionDTO promotionDTO, @PathVariable Long commerceId) throws ProductException, ProductPromotionException, CommerceNotFoundException {
        promotionDTO = this.promotionFacade.createOfferPromotion(promotionDTO, commerceId);

        return promotionDTO;
    }

    @PostMapping("/discount/{commerceId}")
    @PreAuthorize("hasAuthority('SCOPE_MERCHANT')")
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionDTO fireDiscountPromotion (@RequestBody PromotionDTO promotionDTO, @PathVariable Long commerceId) throws ProductException, ProductPromotionException, CommerceNotFoundException {
        promotionDTO = this.promotionFacade.createDiscountPromotion(promotionDTO, commerceId);

        return promotionDTO;
    }

    @GetMapping("/{promotionId}")
    public PromotionDTO promotion (@PathVariable Long promotionId) throws CommerceNotFoundException {
        return this.promotionFacade.getPromotion(promotionId);
    }

    @GetMapping("/commerce/{commerceId}")
    public List<PromotionDTO> promotions (@PathVariable Long commerceId) throws CommerceNotFoundException {
        return this.promotionFacade.getCommercePromotions(commerceId);
    }

    @GetMapping("/")
    public List<PromotionDTO> promotions () {
        return this.promotionFacade.getPromotions();
    }
}
