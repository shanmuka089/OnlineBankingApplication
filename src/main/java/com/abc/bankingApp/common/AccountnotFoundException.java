package com.abc.bankingApp.common;

public class AccountnotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccountnotFoundException(String msg) {
		super(msg);
	}
}
