package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.ProductCategory;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.exception.ProductCategoryNotFoundException;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.services.CommerceService;
import com.mimka.shoplocbe.services.ProductCategoryService;
import com.mimka.shoplocbe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    private final ProductDTOUtil productDTOUtil;

    private final CommerceService commerceService;

    @Autowired
    public ProductFacadeImpl(ProductService productService, ProductCategoryService productCategoryService, ProductDTOUtil productDTOUtil, CommerceService commerceService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.productDTOUtil = productDTOUtil;
        this.commerceService = commerceService;
    }

    @Override
    public ProductDTO getProduct(Long productId) throws ProductException {
        Product product = this.productService.getProduct(productId);

        return this.productDTOUtil.toProductDTO(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        this.productService.deleteProduct(productId);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductException {
        Product product = this.productService.updateProduct(productDTO);

        return this.productDTOUtil.toProductDTO(product);
    }

    @Override
    public void viewProduct(Long productId) throws ProductException {
        this.productService.viewProduct(productId);
    }

    @Override
    public Set<ProductCategoryDTO> getCommerceProductCategories(Long commerceId) throws CommerceNotFoundException {
        Set<ProductCategory> productCategorySet = this.commerceService.getCommerceProductCategories(commerceId);

        return productCategorySet
                .stream()
                .map(productDTOUtil::toProductCategoryDTO).collect(Collectors.toSet());
    }

    @Override
    public ProductCategoryDTO getProductCategory(Long productCategoryId) throws ProductCategoryNotFoundException {
        ProductCategory productCategory = this.productCategoryService.getProductCategory(productCategoryId);

        return this.productDTOUtil.toProductCategoryDTO(productCategory);
    }

    @Override
    public ProductCategoryDTO createProductCategory(Long commerceId, ProductCategoryDTO productCategoryDTO) throws CommerceNotFoundException {
        ProductCategory productCategory = this.productCategoryService.createProductCategory(productCategoryDTO);
        Commerce commerce = this.commerceService.getCommerce(commerceId);

        productCategory.setCommerce(commerce);

        this.productCategoryService.saveProductCategory(productCategory);

        return this.productDTOUtil.toProductCategoryDTO(productCategory);
    }

    @Override
    public ProductCategoryDTO updateProductCategory(Long productCategoryId, ProductCategoryDTO productCategoryDTO) throws ProductCategoryNotFoundException {
        ProductCategory productCategory = this.productCategoryService.updateProductCategory(productCategoryId, productCategoryDTO);

        this.productCategoryService.saveProductCategory(productCategory);

        return this.productDTOUtil.toProductCategoryDTO(productCategory);
    }
}
