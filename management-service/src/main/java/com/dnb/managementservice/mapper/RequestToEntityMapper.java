package com.dnb.managementservice.mapper;

import org.springframework.stereotype.Component;

import com.dnb.managementservice.dto.Loan;
import com.dnb.managementservice.payload.request.LoanRequest;


@Component
	public class RequestToEntityMapper {

		public Loan getAccountEntityObject(LoanRequest loanRequest) {
			Loan loan = new Loan();
			loan.setUserId(loanRequest.getUserId());
			loan.setManagementType(loanRequest.getManagementType());
			loan.setStatus(loanRequest.getStatus());
			loan.setLoanlimit(loanRequest.getLoanlimit());
			return loan;
		}
	}

