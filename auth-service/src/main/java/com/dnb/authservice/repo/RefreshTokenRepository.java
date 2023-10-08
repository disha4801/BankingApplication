package com.dnb.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnb.authservice.dto.RefreshToken;

import jakarta.transaction.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	Optional<RefreshToken> findByToken(String token);

	@Transactional
	public void deleteByUserId(Integer id);

}
