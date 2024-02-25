package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.CommerceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceTypeRepository extends JpaRepository<CommerceType, Long> {

}
