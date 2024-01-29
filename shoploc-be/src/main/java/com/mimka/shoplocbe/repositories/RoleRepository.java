package com.mimka.shoplocbe.repositories;

import com.mimka.shoplocbe.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleId (Long id);
}
