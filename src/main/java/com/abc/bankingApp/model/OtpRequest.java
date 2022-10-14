package com.abc.bankingApp.model;

import lombok.Data;

@Data
public class OtpRequest {

	private Integer otpCode;
	private Long authenticateInstanceId;
}
