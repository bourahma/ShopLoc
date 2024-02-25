package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Long> {

    Commerce findByCommerceIdAndDisabled(Long id, boolean disabled);

    List<Commerce> findByDisabled(boolean disabled);

    List<Commerce> findByCommerceType(CommerceType commerceType);

}
