package com.dnb.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnb.authservice.dto.Role;
import com.dnb.authservice.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
