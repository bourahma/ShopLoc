package com.mimka.shoplocbe.dto.product;

import com.mimka.shoplocbe.entities.Product;
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
}