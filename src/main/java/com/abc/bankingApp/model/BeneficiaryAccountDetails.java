package com.abc.bankingApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeneficiaryAccountDetails {

	private Long accountNumber;
	private String accountHolderName;
}
