package com.dnb.managementservice.service;

import java.util.Optional;

import com.dnb.managementservice.dto.Loan;
import com.dnb.managementservice.exceptions.IdNotFoundException;


public interface LoanService {


  public Optional<Loan> getLoanById(String loanId) throws IdNotFoundException;

  public Iterable<Loan> getAllLoans();

  public Iterable<Loan> getAllLoansByUserId(Integer userId) throws IdNotFoundException;

 public boolean loanExistsById(String loanId);

 public boolean deleteLoanById(String loanId) throws IdNotFoundException;

   public Loan createLoan(Loan loan);

	

}
