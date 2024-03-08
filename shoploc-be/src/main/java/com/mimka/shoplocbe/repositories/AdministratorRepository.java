package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findByUsername(String username);
}