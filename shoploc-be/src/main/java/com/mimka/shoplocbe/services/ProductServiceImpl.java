package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDTOUtil productDTOUtil) {
        this.productRepository = productRepository;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public List<Product> getProductsByCommerce(Long commerceId) {
        return null;
    }

    @Override
    public Product getProduct(Long productId) {
        return this.productRepository.findById(productId).get();
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = this.productDTOUtil.toProduct(productDTO);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        Product product = this.productRepository.findById(productDTO.getProductId()).get();

        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setRewardPointsPrice(productDTO.getRewardPointsPrice());
        product.setPrice(productDTO.getPrice());
        product.setRewardPointsPrice(productDTO.getRewardPointsPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setGift(productDTO.getIsGift());

        return this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
    }

    @Override
    public void viewProduct(Long productId) {
        Product product = this.productRepository.findById(productId).get();
        product.setView(product.getView() + 1);
        this.productRepository.save(product);
    }
}
