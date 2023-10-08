package com.dnb.authservice.exceptions;

public class IdNotFoundException extends Exception {

	static final long serialVersionUID = 1L;

	public IdNotFoundException(String msg) {
		super(msg);
	}

	public String toString() {
		return super.toString() + super.getMessage();
	}
}
