package com.dnb.accountservice.mapper;

import org.springframework.stereotype.Component;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.payload.request.AccountRequest;

@Component
public class RequestToEntityMapper {

	public Account getAccountEntityObject(AccountRequest accountRequest) {
		Account account = new Account();
		account.setAadharNumber(accountRequest.getAadharNumber());
		account.setAccountStatus(accountRequest.getAccountStatus());
		account.setAccountType(accountRequest.getAccountType());
		account.setBalance(accountRequest.getBalance());
		account.setMobileNumber(accountRequest.getMobileNumber());
		account.setPanNumber(accountRequest.getPanNumber());
		account.setUserId(accountRequest.getUserId());
		return account;
	}
}
