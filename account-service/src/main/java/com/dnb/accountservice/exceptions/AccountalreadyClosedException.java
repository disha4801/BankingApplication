package com.dnb.accountservice.exceptions;

public class AccountalreadyClosedException extends Exception {
	public AccountalreadyClosedException(String msg) {
		super(msg);
	}

	@Override
	public String toString() {
		return super.toString() + super.getMessage();
	}
}
