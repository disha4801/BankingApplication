package com.dnb.authservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dnb.authservice.dto.Role;
import com.dnb.authservice.enums.ERole;
import com.dnb.authservice.repo.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }

}
