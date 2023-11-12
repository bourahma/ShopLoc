package com.mimka.shoplocbe.repository;

import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    void deleteByUuid(String uuid);

    RegistrationToken findByUuid(String uuid);

    RegistrationToken findByUser(User user);

    @Modifying
    @Query("UPDATE RegistrationToken rt SET rt.uuid = :newToken WHERE rt.user = :utilisateur")
    void updateToken(@Param("newToken") String newToken, @Param("utilisateur") User utilisateur);

}
