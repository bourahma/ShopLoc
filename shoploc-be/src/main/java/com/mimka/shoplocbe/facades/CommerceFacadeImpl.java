package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.dto.commerce.CommerceTypeDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Address;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommerceFacadeImpl implements CommerceFacade {

    private final CommerceService commerceService;

    private final ProductService productService;

    private final ImageService imageService;

    private final CommerceTypeService commerceTypeService;

    private final CommerceDTOUtil commerceDTOUtil;

    private final ProductDTOUtil productDTOUtil;

    private final AddressService addressService;


    @Autowired
    public CommerceFacadeImpl(CommerceService commerceService, ProductService productService, ImageService imageService, CommerceTypeService commerceTypeService, CommerceDTOUtil commerceDTOUtil, ProductDTOUtil productDTOUtil, AddressService addressService) {
        this.commerceService = commerceService;
        this.productService = productService;
        this.imageService = imageService;
        this.commerceTypeService = commerceTypeService;
        this.commerceDTOUtil = commerceDTOUtil;
        this.productDTOUtil = productDTOUtil;
        this.addressService = addressService;
    }

    @Override
    public CommerceDTO getCommerce(Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.commerceService.getCommerce(commerceId);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }

    @Override
    public CommerceDTO addCommerce(CommerceDTO commerceDTO) throws CommerceTypeNotFoundException {
        // Create commerce address
        Address address = this.addressService.createAddress(commerceDTO.getAddressDTO());
        // Get commerce type if it exists otherwise CommerceTypeNotFoundException is thrown.
        CommerceType commerceType = this.commerceTypeService.getCommerceTypeById(commerceDTO.getCommerceType().getCommerceTypeId());
        Commerce commerce = this.commerceDTOUtil.toCommerce(commerceDTO);
        commerce.setDisabled(false);
        commerce.setCommerceType(commerceType);
        commerce.setAddress(address);
        // Save commerce image to amazon S3 bucket and get back the image url.
        commerce.setImageUrl(this.imageService.uploadImage(commerceDTO.getMultipartFile()));
        commerce = this.commerceService.saveCommerce(commerce);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }

    @Override
    public CommerceDTO addProduct(Long commerceId, ProductDTO productDTO) throws CommerceNotFoundException {
        Product product = this.productService.createProduct(productDTO);
        product.setImageUrl(this.imageService.uploadImage(productDTO.getMultipartFile()));
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
    public List<CommerceDTO> getCommercesByType(Long commerceTypeId) throws CommerceTypeNotFoundException, CommerceNotFoundException {
        CommerceType commerceType = this.commerceTypeService.getCommerceTypeById(commerceTypeId);
        List<Commerce> commerces = this.commerceService.getCommercesByType(commerceType);

        return commerces
                .stream()
                .map(commerceDTOUtil::toCommerceDTO).toList();
    }

    @Override
    public void disableCommerce(Long commerceId) {
        this.commerceService.disableCommerce(commerceId);
    }

    @Override
    public List<CommerceTypeDTO> getCommerceTypes() {
        List<CommerceType> commerceTypeList = this.commerceTypeService.getCommerceTypes();

        return commerceTypeList
                .stream()
                .map(commerceDTOUtil::toCommerceTypeDTO).toList();
    }

    @Override
    public CommerceTypeDTO createCommerceType(CommerceTypeDTO commerceTypeDTO) {
        CommerceType commerceType = this.commerceTypeService.createCommerceType(commerceTypeDTO);

        return this.commerceDTOUtil.toCommerceTypeDTO(commerceType);
    }

    @Override
    public CommerceDTO updateCommerce(CommerceDTO commerceDTO) throws CommerceNotFoundException, CommerceTypeNotFoundException {
        // Create the new address if any.
        Address address = this.addressService.createAddress(commerceDTO.getAddressDTO());
        // Update the address
        Commerce commerce = this.commerceService.updateCommerce(commerceDTO);
        // Update the commerce image if any
        commerce.setImageUrl(this.imageService.uploadImage(commerceDTO.getMultipartFile()));
        // Get commerce type.
        CommerceType commerceType = this.commerceTypeService.getCommerceTypeById(commerceDTO.getCommerceType().getCommerceTypeId());
        commerce.setAddress(address);
        commerce.setCommerceType(commerceType);

        this.commerceService.saveCommerce(commerce);

        return this.commerceDTOUtil.toCommerceDTO(commerce);
    }
}
