package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.GiftHistoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;

import java.util.List;

public interface GiftFacade {

    List<ProductDTO> getGifts ();

    List<ProductDTO> getCommerceGifts (Long commerceId) throws CommerceNotFoundException;

    GiftHistoryDTO availGift (String customerUsername, Long productId) throws ProductException;

    List<GiftHistoryDTO> getCustomerGiftsHistory (String customerUsername);

    List<ProductDTO> getGiftsPerCommerceType(long commerceTypeId) throws CommerceTypeNotFoundException;
}
