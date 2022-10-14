package com.abc.bankingApp.model;

import lombok.Data;

@Data
public class StatusResponse {

	private String status;
	private Object data;
	private ErrorResponse errors;
}
