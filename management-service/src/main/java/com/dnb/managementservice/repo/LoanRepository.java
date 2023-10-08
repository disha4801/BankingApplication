package com.dnb.managementservice.repo;

import org.springframework.data.repository.CrudRepository;

import com.dnb.managementservice.dto.Loan;

public interface LoanRepository extends CrudRepository<Loan, String> {

	Iterable<Loan> findAllByUserId(Integer userId);

}
