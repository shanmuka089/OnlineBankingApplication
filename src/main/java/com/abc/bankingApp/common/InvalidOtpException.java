package com.abc.bankingApp.common;

public class InvalidOtpException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidOtpException(String msg) {
		super(msg);
	}
}
