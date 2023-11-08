package com.mimka.shoplocbe.repository;

import com.mimka.shoplocbe.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    void deleteByUuid(String uuid);

    RegistrationToken findByUuid(String uuid);
}
