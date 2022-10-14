package com.abc.bankingApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abc.bankingApp.common.AccountnotFoundException;
import com.abc.bankingApp.common.AuthenticationFailedException;
import com.abc.bankingApp.common.UserNotFoundException;
import com.abc.bankingApp.model.AccountDetails;
import com.abc.bankingApp.model.BeneficiaryAccountDetails;
import com.abc.bankingApp.model.User;
import com.abc.bankingApp.repository.AccountDetailsRepository;
import com.abc.bankingApp.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AccountDetailsRepository accountDetailsRepo;

	@Override
	public AccountDetails getAccountByUserId(Integer id) {
		String transactionUser = userRepo.findById(id).get().getEmailId();
		String authenticatedUser = getAuthentication().getName();
		
//		checking User has right authority to get the data
		if(!transactionUser.equals(authenticatedUser)) {
			throw new AuthenticationFailedException("you don't have authority to access data with these userId!...");
		}
		
		Optional<User> optionalUser = userRepo.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("account not exist for given user id");
		}
		User user = optionalUser.get();
		Optional<AccountDetails> optionalAccount = accountDetailsRepo.findById(user.getAccountNumber());
		if (optionalAccount.isEmpty()) {
			throw new AccountnotFoundException("Account details doesn't exist with given account number!");
		}
		return optionalAccount.get();
	}

	@Override
	public List<BeneficiaryAccountDetails> getBeneficiaryAccounts() {
		Long accountNumber=getAccountNumber(getAuthentication());
		List<AccountDetails> allAccountsDetails = accountDetailsRepo.findAll();
		List<BeneficiaryAccountDetails> benificiaryAccounts= allAccountsDetails.stream().filter(accountDetails -> {
			return !accountDetails.getAccountNumber().equals(accountNumber);
		}).map(beneficiaryAccount->{
			return new BeneficiaryAccountDetails(beneficiaryAccount.getAccountNumber(),beneficiaryAccount.getAccountHolderName());
		}).collect(Collectors.toList());

		return benificiaryAccounts;
	}
	
	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@Override
	public Long getAccountNumber(Authentication authentication) {
		String name = authentication.getName();
		User user = userRepo.findByEmailId(name).get();
		return user.getAccountNumber();
	}

	@Override
	public AccountDetails getAccountByAccountNumber(Long accountNumber) {
		Optional<AccountDetails> optionalAccountDetails = accountDetailsRepo.findById(accountNumber);
		if(optionalAccountDetails.isPresent()) {
			return optionalAccountDetails.get();
		}
		return null;
	}
	
	@Override
	public AccountDetails saveAccountSummary(AccountDetails accountDetails) {
		return accountDetailsRepo.save(accountDetails);
	}

}
