package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.product.GiftHistoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.facades.GiftFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/gift")
@CrossOrigin(origins = "${allowed.origin}")
public class GiftController {

    private final GiftFacade giftFacade;

    @Autowired
    public GiftController(GiftFacade giftFacade) {
        this.giftFacade = giftFacade;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<ProductDTO> gifts ( ) {
        return this.giftFacade.getGifts();
    }

    @GetMapping("/{commerceId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<ProductDTO> giftsPerCommerce (@PathVariable long commerceId) throws CommerceNotFoundException {
        return this.giftFacade.getCommerceGifts(commerceId);
    }

    @GetMapping("/commerce-type/{commerceTypeId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<ProductDTO> giftsPerCommerceType (@PathVariable long commerceTypeId) throws CommerceTypeNotFoundException {
        return this.giftFacade.getGiftsPerCommerceType(commerceTypeId);
    }

    @GetMapping("/avail/{productId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public GiftHistoryDTO availGift (Principal principal, @PathVariable long productId) throws ProductException {
        return this.giftFacade.availGift(principal.getName(), productId);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<GiftHistoryDTO> customerGiftsHistory (Principal principal)  {
        return this.giftFacade.getCustomerGiftsHistory(principal.getName());
    }
}
