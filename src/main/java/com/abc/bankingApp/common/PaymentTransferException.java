package com.abc.bankingApp.common;

public class PaymentTransferException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public PaymentTransferException(String msg) {
		super(msg);
	}

}
