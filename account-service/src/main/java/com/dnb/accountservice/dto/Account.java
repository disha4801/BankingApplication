package com.dnb.accountservice.dto;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import com.dnb.accountservice.enums.AccountType;
import com.dnb.accountservice.utils.CustomIdGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_seq")
	@GenericGenerator(name="account_seq",strategy="com.dnb.accountservice.utils.CustomIdGenerator",
	parameters =  {@Parameter(name=CustomIdGenerator.INCREMENT_PARAM,value="50"),
			@Parameter(name=CustomIdGenerator.FLAG_PARAMETER,value="false"),
			@Parameter(name=CustomIdGenerator.VALUE_PREFIX_PARAMETER,value="Acc_"),
			@Parameter(name=CustomIdGenerator.NUMBER_FORMAT_PARAMETER,value="%05d")}
			)
	private String accountId;
	private Integer userId;
	private AccountType accountType;
	private String panNumber;
	private String aadharNumber;
	private String mobileNumber;
	private Boolean accountStatus;
	@Min(value=10000)
	private long balance;
}
