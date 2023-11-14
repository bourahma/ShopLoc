package com.mimka.shoplocbe.repository;

import com.mimka.shoplocbe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleId (Long id);
}
