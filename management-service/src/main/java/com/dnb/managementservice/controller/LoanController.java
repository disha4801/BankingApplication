package com.dnb.managementservice.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.managementservice.dto.Loan;
import com.dnb.managementservice.exceptions.DataNotFoundException;
import com.dnb.managementservice.exceptions.IdNotFoundException;
import com.dnb.managementservice.mapper.RequestToEntityMapper;
import com.dnb.managementservice.payload.request.LoanRequest;
import com.dnb.managementservice.service.LoanService;
import jakarta.validation.Valid;

@RestController

@RequestMapping("/api/loan")

@CrossOrigin("*")

public class LoanController {

	@Autowired

	private LoanService loanService;

	@Autowired
	private RequestToEntityMapper requestToEntityMapper;
	@PostMapping("/create")
	public ResponseEntity<?> createLoan(@Valid @RequestBody LoanRequest loanRequest) {
		Loan loan = requestToEntityMapper.getAccountEntityObject(loanRequest);
		Loan loan2 = loanService.createLoan(loan);
		return new ResponseEntity(loan2, HttpStatus.CREATED);

	}

	@GetMapping("/{loanId}")

	public ResponseEntity<?> getLoanById(@PathVariable("loanId") String loanId) throws IdNotFoundException {
		Optional<Loan> optional = loanService.getLoanById(loanId);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());

		}

		else {

			throw new IdNotFoundException("Id not found");

		}

	}

	@GetMapping("/all")

	public ResponseEntity<?> getAllLoans() throws DataNotFoundException {

		List<Loan> loans = (List<Loan>) loanService.getAllLoans();

		if (loans.isEmpty()) {

			throw new DataNotFoundException("Data not found");

		}

		else {

			return ResponseEntity.ok(loans);

		}

	}

	@GetMapping("/all/{userId}")

	public ResponseEntity<?> getAllCreditsByUserId(@PathVariable("userId") Integer userId)
			throws IdNotFoundException, DataNotFoundException {

		List<Loan> loans = (List<Loan>) loanService.getAllLoansByUserId(userId);

		if (loans.isEmpty()) {

			throw new DataNotFoundException("Data not found");

		}

		else {

			return ResponseEntity.ok(loans);

		}

	}

	@DeleteMapping("/{loanId}")

	public ResponseEntity<?> deleteCreditById(@PathVariable("loanId") String loanId) throws IdNotFoundException {
		if (loanService.loanExistsById(loanId)) {
			loanService.deleteLoanById(loanId);
			return ResponseEntity.noContent().build();

		}

		else {

			throw new IdNotFoundException("Id is not valid");

		}

	}

}
