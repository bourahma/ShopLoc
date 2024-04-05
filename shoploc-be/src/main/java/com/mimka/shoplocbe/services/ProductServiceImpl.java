package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.ProductException;
import com.mimka.shoplocbe.repositories.ProductRepository;
import com.mimka.shoplocbe.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final PromotionRepository promotionRepository;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDTOUtil productDTOUtil, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.productDTOUtil = productDTOUtil;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Product getProduct(Long productId) throws ProductException {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found for ID : " + productId));
    }

    @Override
    public List<Product> getGiftProducts() {
        return this.productRepository.findByGiftIsTrue();
    }

    @Override
    public List<Product> getCommerceGiftProducts(Commerce commerce) {
        return this.productRepository.findByCommerceAndGiftIsTrue(commerce);
    }

    @Override
    public List<Product> getGiftProductsPerCommerceType(CommerceType commerceType) {
        return this.productRepository.findByCommerce_CommerceType(commerceType);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = this.productDTOUtil.toProduct(productDTO);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) throws ProductException {
        Product product = this.getProduct(productDTO.getProductId());

        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setRewardPointsPrice(productDTO.getRewardPointsPrice());
        product.setPrice(productDTO.getPrice());
        product.setRewardPointsPrice(productDTO.getRewardPointsPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setGift(productDTO.isGift());
        product.setPromotion(
                productDTO.getPromotionId() != null ? this.promotionRepository.findById(productDTO.getPromotionId()).orElse(null) : null
        );

        return this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
    }

    @Override
    public void viewProduct(Long productId) throws ProductException {
        Product product = this.getProduct(productId);
        product.setView(product.getView() + 1);
        this.productRepository.save(product);
    }
}
