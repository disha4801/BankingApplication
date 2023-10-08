package com.dnb.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnb.authservice.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    public Optional<User> findByEmail(String email);
    
}
