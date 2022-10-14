package com.abc.bankingApp.services;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.abc.bankingApp.model.AccountDetails;
import com.abc.bankingApp.model.BeneficiaryAccountDetails;

public interface AccountService {

	AccountDetails getAccountByUserId(Integer id);
	List<BeneficiaryAccountDetails> getBeneficiaryAccounts();
	AccountDetails getAccountByAccountNumber(Long accountNumber);
	Long getAccountNumber(Authentication authentication);
	Authentication getAuthentication();
	AccountDetails saveAccountSummary(AccountDetails accountDetails);
}
