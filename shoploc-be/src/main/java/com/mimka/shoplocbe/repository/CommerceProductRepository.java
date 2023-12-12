package com.mimka.shoplocbe.repository;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import com.mimka.shoplocbe.entity.CommerceProduct;
import com.mimka.shoplocbe.entity.CommerceProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommerceProductRepository extends JpaRepository<CommerceProduct, CommerceProductId> {

    @Query("SELECT NEW com.mimka.shoplocbe.dto.product.ProductDTO(p.productId, p.productName, p.description, p.price, p.quantity, cp.commerce.commerceId) FROM Product p JOIN CommerceProduct cp ON p.productId = cp.id.productId WHERE cp.id.commerceId = :commerceId")
    List<ProductDTO> findProductsByCommerceId(@Param("commerceId") Long commerceId);
}

