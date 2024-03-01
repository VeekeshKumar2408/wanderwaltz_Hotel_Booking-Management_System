package com.veekeshsingh.WanderWaltz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.veekeshsingh.WanderWaltz.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);

    boolean existsByName(Role role);
}
