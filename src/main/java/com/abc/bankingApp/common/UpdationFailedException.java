package com.abc.bankingApp.common;

public class UpdationFailedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UpdationFailedException(String msg) {
		super(msg);
	}
}
