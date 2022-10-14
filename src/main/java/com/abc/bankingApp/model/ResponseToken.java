package com.abc.bankingApp.model;

import lombok.Data;

@Data
public class ResponseToken {
	
	private String token;
	private String tokenType;
	private Integer userId;

}
