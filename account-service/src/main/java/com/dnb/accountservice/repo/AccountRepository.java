package com.dnb.accountservice.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dnb.accountservice.dto.Account;

import jakarta.transaction.Transactional;

@Repository
public interface AccountRepository extends CrudRepository<Account,String>{
	// delete from profile where userId = ?
	@Transactional
	public void deleteByUserId(Integer userId);

	// select * from profile where userId = ?
	public Optional<Account> findByUserId(Integer userId);

}
