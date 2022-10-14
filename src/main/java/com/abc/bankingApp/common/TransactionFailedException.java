package com.abc.bankingApp.common;

public class TransactionFailedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TransactionFailedException(String msg) {
		super(msg);
	}
}
