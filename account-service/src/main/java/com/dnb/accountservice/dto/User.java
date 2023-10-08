package com.dnb.accountservice.dto;

import lombok.Data;

@Data
public class User {
	
	private Integer userId;
	private String username;
	private String emailId;
	private String password;

}