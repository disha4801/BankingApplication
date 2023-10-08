package com.dnb.managementservice.dto;


import com.dnb.managementservice.enums.ManagementType;
import com.dnb.managementservice.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String accountId;
	private Integer userId;
	private ManagementType managementType;
	private Status status;
	@Min(value=10000)
	private long Loanlimit;
	
	

}

