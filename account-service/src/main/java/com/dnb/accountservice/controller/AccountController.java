package com.dnb.accountservice.controller;

import java.util.Optional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exceptions.AccountalreadyClosedException;
import com.dnb.accountservice.exceptions.DataNotFoundException;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.exceptions.InsufficientBalanceException;
import com.dnb.accountservice.mapper.RequestToEntityMapper;
import com.dnb.accountservice.payload.request.AccountRequest;
import com.dnb.accountservice.payload.request.AmountRequest;
import com.dnb.accountservice.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	RequestToEntityMapper requestToEntityMapper;
	
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest){
		Account account = requestToEntityMapper.getAccountEntityObject(accountRequest);
		try {
			Account account2 = accountService.createAccount(account);
			return new ResponseEntity(account2,HttpStatus.CREATED);
		}
		catch(IdNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/acc/{accountId}")
	public ResponseEntity<?>getAccountById(@PathVariable("accountId")String accountId) throws IdNotFoundException{
		Optional<Account>optional = accountService.getAccountById(accountId);
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		else {
			throw new IdNotFoundException("Id not found");
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?>getAllAccounts() throws DataNotFoundException{
		List<Account>accounts=(List<Account>) accountService.getAllAccounts();
		if(accounts.isEmpty()) {
			throw new DataNotFoundException("Data not found");
		}
		else {
			return ResponseEntity.ok(accounts);
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?>getAccountByUserId(@PathVariable("userId")Integer userId) throws IdNotFoundException{
		Optional<Account>optional=accountService.getAccountByUserId(userId);
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		else {
			throw new IdNotFoundException("Id is not valid");
		}
	}
	
	@DeleteMapping("/{profileUUID}")
	public ResponseEntity<?> deleteAccountById(@PathVariable("accountId")String accountId) throws IdNotFoundException{
		if(accountService.accountExistsById(accountId)) {
			accountService.deleteAccountbyId(accountId);
			return ResponseEntity.noContent().build();
		}
		else {
			throw new IdNotFoundException("Id is not valid");
		}
	}
	
	@PutMapping("/deposit/{accountId}")
	public ResponseEntity<?> deposit(@PathVariable("accountId") String accountId, @RequestBody AmountRequest amountRequest) throws IdNotFoundException {
		Optional<Account> optional = accountService.getAccountById(accountId);
		if(optional.isPresent()) {
			Account account=accountService.depositAmount(accountId, amountRequest.getAmount());
			return ResponseEntity.ok(account);
		}
		else {
			throw new IdNotFoundException("Id not found");
		}
	}
	
	@PostMapping("/close/{accountId}")
	public ResponseEntity<?> closeAccount(@PathVariable("accountId")String accountId) throws IdNotFoundException, AccountalreadyClosedException{
		Account account = accountService.closeAccount(accountId);
		return ResponseEntity.ok(account);
	}
	
	@PutMapping("/withdraw/{accountId}")
	public ResponseEntity<?> withdraw(@PathVariable("accountId") String accountId, @RequestBody AmountRequest amountRequest) throws IdNotFoundException, InsufficientBalanceException {
		Optional<Account> optional = accountService.getAccountById(accountId);
		if(optional.isPresent()) {
			Account account=accountService.withdrawAmount(accountId, amountRequest.getAmount());
			return ResponseEntity.ok(account);
		}
		else {
			throw new IdNotFoundException("Id not found");
		}
	}
}
