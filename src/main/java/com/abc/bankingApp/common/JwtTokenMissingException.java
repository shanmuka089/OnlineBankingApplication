package com.abc.bankingApp.common;

public class JwtTokenMissingException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public JwtTokenMissingException(String msg) {
		super(msg);
	}

}
