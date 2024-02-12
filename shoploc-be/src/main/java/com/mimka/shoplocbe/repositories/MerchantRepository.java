package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Merchant findByUsername(String username);

    Merchant findByEmail(String email);
}
