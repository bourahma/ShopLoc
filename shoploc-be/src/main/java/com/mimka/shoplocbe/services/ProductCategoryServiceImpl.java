package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.product.ProductCategoryDTO;
import com.mimka.shoplocbe.dto.product.ProductDTOUtil;
import com.mimka.shoplocbe.entities.ProductCategory;
import com.mimka.shoplocbe.exception.ProductCategoryNotFoundException;
import com.mimka.shoplocbe.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductDTOUtil productDTOUtil;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository, ProductDTOUtil productDTOUtil) {
        this.productCategoryRepository = productCategoryRepository;
        this.productDTOUtil = productDTOUtil;
    }

    @Override
    public ProductCategory getProductCategory(Long productCategoryId) throws ProductCategoryNotFoundException {
        Optional<ProductCategory> productCategory = this.productCategoryRepository.findById(productCategoryId);
        if (!productCategory.isPresent()) {
            throw new ProductCategoryNotFoundException("Aucune catégorie n'est trouvée pour l'id : " + productCategoryId);
        }
        return productCategory.get();
    }

    @Override
    public ProductCategory createProductCategory(ProductCategoryDTO productCategoryDTO) {
        return this.productDTOUtil.toProductCategory(productCategoryDTO);
    }

    @Override
    public void saveProductCategory(ProductCategory productCategory) {
        this.productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(Long productCategoryId, ProductCategoryDTO productCategoryDTO) throws ProductCategoryNotFoundException {
        ProductCategory productCategory = this.getProductCategory(productCategoryId);

        productCategory.setDescription(productCategoryDTO.getDescription());
        productCategory.setLabel(productCategoryDTO.getLabel());

        return productCategory;
    }
}
