package com.mimka.shoplocbe.dto.product;

import com.mimka.shoplocbe.entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOUtil {
    private ModelMapper modelMapper;

    @Autowired
    ProductDTOUtil (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }
    public Product toProduct (ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO (Product product) {
        ProductDTO productDTO = this.modelMapper.map(product, ProductDTO.class);
        productDTO.setPromotion(product.getPromotion() != null ? this.toPromotionDTO(product.getPromotion()) : null);
        productDTO.setCommerceId(product.getCommerce().getCommerceId());

        return productDTO;
    }

    public ProductCategoryDTO toProductCategoryDTO (ProductCategory productCategory) {
        ProductCategoryDTO productCategoryDTO = this.modelMapper.map(productCategory, ProductCategoryDTO.class);
        productCategoryDTO.setCommerceId(productCategory.getCommerce().getCommerceId());

        return productCategoryDTO;
    }

    public GiftHistoryDTO toGiftHistoryDTO (GiftHistory giftHistory) {
        GiftHistoryDTO giftHistoryDTO = this.modelMapper.map(giftHistory, GiftHistoryDTO.class);

        return giftHistoryDTO;
    }

    public ProductCategory toProductCategory (ProductCategoryDTO productCategoryDTO) {
        return this.modelMapper.map(productCategoryDTO, ProductCategory.class);
    }

    public Promotion toPromotion (PromotionDTO promotionDTO) {
        return this.modelMapper.map(promotionDTO, Promotion.class);
    }

    public PromotionDTO toPromotionDTO(Promotion promotion) {
        return this.modelMapper.map(promotion, PromotionDTO.class);
    }

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<Product, ProductDTO>() {
            @Override
            protected void configure() {
                skip(destination.getPromotion());
            }
        });
    }
}