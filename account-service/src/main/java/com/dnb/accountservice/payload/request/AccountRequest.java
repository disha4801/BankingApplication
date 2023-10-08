package com.dnb.accountservice.payload.request;


import com.dnb.accountservice.enums.AccountType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AccountRequest {
	private Integer userId;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	private String panNumber;
	private String aadharNumber;
	private String mobileNumber;
	private Boolean accountStatus;
	private int balance;
}
