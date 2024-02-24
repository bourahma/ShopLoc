package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.api.map.MapAPI;
import com.mimka.shoplocbe.dto.commerce.AddressDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.services.CommerceService;
import com.mimka.shoplocbe.services.ImageService;
import com.mimka.shoplocbe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CommerceFacadeImpl implements CommerceFacade {

    private final CommerceService commerceService;

    private final ProductService productService;

    private final ImageService imageService;

    private final MapAPI mapAPI;

    private final CommerceDTOUtil commerceDTOUtil;

    private final ProductDTOUtil productDTOUtil;


    @Autowired
    public CommerceFacadeImpl(CommerceService commerceService, ProductService productService, ImageService imageService, MapAPI mapAPI, CommerceDTOUtil commerceDTOUtil, ProductDTOUtil productDTOUtil) {
        this.commerceService = commerceService;
        this.productService = productService;
        this.imageService = imageService;
        this.mapAPI = mapAPI;
        this.commerceDTOUtil = commerceDTOUtil;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public CommerceDTO getCommerce(Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }

    @Override
    public CommerceDTO addCommerce(CommerceDTO commerceDTO) {
        AddressDTO addressDTO = this.mapAPI.getCoordinates(commerceDTO.getAddressDTO());
        commerceDTO.setAddressDTO(addressDTO);

        Commerce commerce = this.commerceService.createCommerce(commerceDTO);
        String imageUrl = "URL:/commerce-image";
        try {
            imageUrl = this.imageService.uploadImage(commerceDTO.getMultipartFile());
        } catch (Exception e) {
            System.out.println("Error");
        }
        commerce.setImageUrl(imageUrl);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }

    @Override
    public CommerceDTO addProduct(Long commerceId, ProductDTO productDTO) throws CommerceNotFoundException {
        Product product = this.productService.createProduct(productDTO);
        String imageUrl = "URL:/product-image";
        try {
            imageUrl = this.imageService.uploadImage(productDTO.getMultipartFile());
        } catch (Exception e) {
            System.out.println("Error"); // TODO : create amazon S3
        }
        product.setImageUrl(imageUrl);
        Commerce commerce = this.commerceService.addProduct(product, commerceId);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }

    @Override
    public List<ProductDTO> getCommerceProducts(Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);
        List<Product> products = commerce.getProducts();

        return products
                .stream()
                .map(productDTOUtil::toProductDTO).toList();
    }

    @Override
    public List<CommerceDTO> getCommerces() {
        List<Commerce> commerces = this.commerceService.getCommerces();

        return commerces
                .stream()
                .map(commerceDTOUtil::toCommerceDTO).toList();
    }

    @Override
    public List<CommerceDTO> getCommerceByTypes(String commerceType) {
        List<Commerce> commerces = this.commerceService.getCommerces();

        return commerces
                .stream()
                .map(commerceDTOUtil::toCommerceDTO).toList();
    }

    @Override
    public void disableCommerce(Long commerceId) {
        this.commerceService.disableCommerce(commerceId);
    }

    @Override
    public CommerceDTO updateCommerce(CommerceDTO commerceDTO) throws CommerceNotFoundException {
        AddressDTO addressDTO = this.mapAPI.getCoordinates(commerceDTO.getAddressDTO());
        commerceDTO.setAddressDTO(addressDTO);

        Commerce commerce = this.commerceService.updateCommerce(commerceDTO);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }

}
