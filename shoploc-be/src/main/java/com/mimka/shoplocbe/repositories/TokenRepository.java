package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    Token findTokenByUuid(String uuid);
}
