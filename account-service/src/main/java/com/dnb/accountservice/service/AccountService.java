package com.dnb.accountservice.service
;

import java.util.Optional;


import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exceptions.AccountalreadyClosedException;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.exceptions.InsufficientBalanceException;

public interface AccountService {
	public Account createAccount(Account account) throws IdNotFoundException;
	
	public Optional<Account>getAccountById(String accountId) throws IdNotFoundException;
	
	public Iterable<Account>getAllAccounts();
	
	public Optional<Account>getAccountByUserId(Integer userId);
	
	public boolean accountExistsById(String accountId);
	
	public boolean deleteAccountbyId(String accountId)throws IdNotFoundException;
	
	public void deleteAccountByUserId(Integer userId);

	public Account depositAmount(String accountId, long balance) throws IdNotFoundException;

	public Account withdrawAmount(String accountId, long balance) throws IdNotFoundException, InsufficientBalanceException;

	public Account closeAccount(String accountId)throws IdNotFoundException,AccountalreadyClosedException;
}
