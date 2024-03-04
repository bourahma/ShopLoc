package com.mimka.shoplocbe.dto.product;

import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.ProductCategory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOUtil {
    private ModelMapper modelMapper;

    @Autowired
    ProductDTOUtil (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public Product toProduct (ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO (Product product) {
        ProductDTO productDTO = this.modelMapper.map(product, ProductDTO.class);
        productDTO.setCommerceId(product.getCommerce().getCommerceId());

        return productDTO;
    }

    public ProductCategoryDTO toProductCategoryDTO (ProductCategory productCategory) {
        ProductCategoryDTO productCategoryDTO = this.modelMapper.map(productCategory, ProductCategoryDTO.class);
        productCategoryDTO.setCommerceId(productCategory.getCommerce().getCommerceId());

        return productCategoryDTO;
    }

    public ProductCategory toProductCategory (ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = this.modelMapper.map(productCategoryDTO, ProductCategory.class);

        return productCategory;
    }
}