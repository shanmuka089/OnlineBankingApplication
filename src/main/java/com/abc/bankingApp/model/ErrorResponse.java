package com.abc.bankingApp.model;

import lombok.Data;

@Data
public class ErrorResponse {
	private Integer code;
	private String message;
}
