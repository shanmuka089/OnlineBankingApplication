package com.abc.bankingApp.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthenticationRequest {

	@NotNull(message = "email id cannot be null")
	private String emailId;
	@NotNull(message = "please enter password")
	private String password;
}
