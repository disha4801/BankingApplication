package com.dnb.managementservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnb.managementservice.dto.Loan;
import com.dnb.managementservice.exceptions.IdNotFoundException;
import com.dnb.managementservice.repo.LoanRepository;

@Service("loanServiceImpl")
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Override
	public Loan createLoan(Loan loan) {
		return loanRepository.save(loan);
	}

	@Override

	public Optional<Loan> getLoanById(String loanId) throws IdNotFoundException {

		return loanRepository.findById(loanId);

	}

	@Override

	public Iterable<Loan> getAllLoans() {

		return loanRepository.findAll();

	}

	@Override

	public Iterable<Loan> getAllLoansByUserId(Integer userId) throws IdNotFoundException {

		return loanRepository.findAllByUserId(userId);

	}

	@Override

	public boolean loanExistsById(String loanId) {

		if (loanRepository.existsById(loanId))
			return true;

		else
			return false;

	}

	@Override

	public boolean deleteLoanById(String loanId) throws IdNotFoundException {

		if (loanRepository.existsById(loanId)) {

			loanRepository.deleteById(loanId);

			if (loanRepository.existsById(loanId)) {

				return false;

			}

			return true;

		}

		else {

			throw new IdNotFoundException("Card Id not found");

		}

	}

}
