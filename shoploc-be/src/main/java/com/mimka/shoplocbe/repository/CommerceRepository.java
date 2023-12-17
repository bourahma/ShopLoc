package com.mimka.shoplocbe.repository;

import com.mimka.shoplocbe.entity.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Long> {

    Commerce findByCommerceId(Long id);

}
