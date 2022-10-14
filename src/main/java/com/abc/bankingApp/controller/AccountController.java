package com.abc.bankingApp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.bankingApp.model.AccountDetails;
import com.abc.bankingApp.model.BeneficiaryAccountDetails;
import com.abc.bankingApp.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	private Logger logger=LogManager.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<AccountDetails> getAccountById(@PathVariable("userId") Integer id){
		logger.info("user of userId:"+id+" calling the get account Method");
		AccountDetails accountDetails = accountService.getAccountByUserId(id);
		logger.info("after getting account details:"+accountDetails);
		return new ResponseEntity<AccountDetails>(accountDetails,HttpStatus.OK);
	}
	
	@GetMapping("/beneficary/all")
	public ResponseEntity<List<BeneficiaryAccountDetails>> getBeneficaryAccount(){
		logger.debug("getting beneficiary accounts method called");
		List<BeneficiaryAccountDetails> beneficiaryAccounts = accountService.getBeneficiaryAccounts();
		logger.debug("after getting beneficiary accounts data");
		return new ResponseEntity<List<BeneficiaryAccountDetails>>(beneficiaryAccounts,HttpStatus.OK);
	}

}
