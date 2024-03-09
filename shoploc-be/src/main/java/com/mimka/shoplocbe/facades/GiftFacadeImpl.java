package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.GiftHistoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftFacadeImpl implements  GiftFacade {

    private final ProductService productService;

    private final CommerceService commerceService;

    private  final CommerceTypeService commerceTypeService;

    private final GiftService giftService;

    private final ProductDTOUtil productDTOUtil;

    private final CustomerService customerService;

    @Autowired
    public GiftFacadeImpl(ProductService productService, CommerceService commerceService, CommerceTypeService commerceTypeService, GiftService giftService, ProductDTOUtil productDTOUtil, CustomerService customerService) {
        this.productService = productService;
        this.commerceService = commerceService;
        this.commerceTypeService = commerceTypeService;
        this.giftService = giftService;
        this.productDTOUtil = productDTOUtil;
        this.customerService = customerService;
    }

    @Override
    public List<ProductDTO> getGifts() {
        List<Product> giftProducts = this.productService.getGiftProducts();

        return giftProducts
                .stream()
                .map(productDTOUtil::toProductDTO).toList();
    }

    @Override
    public List<ProductDTO> getCommerceGifts (Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        List<Product> giftProducts = this.productService.getCommerceGiftProducts(commerce);

        return giftProducts
                .stream()
                .map(productDTOUtil::toProductDTO).toList();
    }

    @Override
    public GiftHistoryDTO availGift(String customerUsername, Long productId) throws ProductException {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);
        Product product = this.productService.getProduct(productId);

        GiftHistory giftHistory = this.giftService.availGift(customer, product);

        return this.productDTOUtil.toGiftHistoryDTO(giftHistory);
    }

    @Override
    public List<GiftHistoryDTO> getCustomerGiftsHistory(String customerUsername) {
        Customer customer = this.customerService.getCustomerByUsername(customerUsername);
        List<GiftHistory> customerGiftsHistory = this.giftService.getCustomerGiftsHistory(customer);

        return customerGiftsHistory
                .stream()
                .map(productDTOUtil::toGiftHistoryDTO).toList();
    }

    @Override
    public List<ProductDTO> getGiftsPerCommerceType(long commerceTypeId) throws CommerceTypeNotFoundException {
        CommerceType commerceType = this.commerceTypeService.getCommerceTypeById(commerceTypeId);
        List<Product> giftProducts = this.productService.getGiftProductsPerCommerceType(commerceType);

        return giftProducts
                .stream()
                .map(productDTOUtil::toProductDTO).toList();
    }
}
