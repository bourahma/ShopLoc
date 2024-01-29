package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Long> {

    Commerce findByCommerceId(Long id);

}
