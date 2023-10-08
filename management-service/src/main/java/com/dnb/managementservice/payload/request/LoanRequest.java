package com.dnb.managementservice.payload.request;


import com.dnb.managementservice.enums.ManagementType;
import com.dnb.managementservice.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class LoanRequest {
	
	private Integer userId;
	@Enumerated(EnumType.STRING)
	private ManagementType managementType;
	private Status status;
	
	private Integer Loanlimit;
	
	

}


