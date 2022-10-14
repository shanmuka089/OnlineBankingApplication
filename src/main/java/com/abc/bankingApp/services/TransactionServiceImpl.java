package com.abc.bankingApp.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.bankingApp.common.AuthenticationFailedException;
import com.abc.bankingApp.common.InsufficientFundsException;
import com.abc.bankingApp.common.PaymentTransferException;
import com.abc.bankingApp.model.AccountDetails;
import com.abc.bankingApp.model.TransactionDetails;
import com.abc.bankingApp.model.TransactionDetailsByDate;
import com.abc.bankingApp.repository.TransactionDetailRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountServiceImpl accountService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private TransactionDetailRepository transactionDetailRepo;

	@Override
	@Transactional
	public Boolean doTransaction(TransactionDetails details) {
		
		double makePayment = details.getAmount();
//		makePayment should be greater than 1000
		if (makePayment < 1000) {
			throw new PaymentTransferException("makePayment transfer should be more than 1000!...");
		}
//		Calculating value is roundUp to nearest thousand
		double result = makePayment % 1000;
		if (result > 500) {
			makePayment = makePayment - result + 1000;
		} else {
			makePayment = makePayment - result;
		}
//		sender account details
		Long senderAccountNumber = accountService.getAccountNumber(accountService.getAuthentication());
		AccountDetails senderAccount = accountService.getAccountByAccountNumber(senderAccountNumber);
		double senderBalance = senderAccount.getCurrentBalance();
		if (senderBalance < makePayment) {
			throw new InsufficientFundsException("Insufficient balance!...");
		}
		senderBalance = senderBalance - makePayment;
		senderAccount.setCurrentBalance(senderBalance);
		accountService.saveAccountSummary(senderAccount);
//		do transaction 
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		System.out.println(LocalDateTime.now(zoneId));
		details.setTransactionDate(LocalDateTime.now(zoneId));
		details.setSenderAccountNumber(senderAccountNumber);
		details.setAmount(makePayment);
		TransactionDetails transactionDetails = transactionDetailRepo.save(details);
//		receiver account details;
		Long receiverAccountnumber = details.getReceiverAccountNumber();
		AccountDetails receiverAccount = accountService.getAccountByAccountNumber(receiverAccountnumber);
		double receiverBalance = receiverAccount.getCurrentBalance();
		receiverBalance = receiverBalance + makePayment;
		receiverAccount.setCurrentBalance(receiverBalance);
		if (transactionDetails != null) {
			accountService.saveAccountSummary(receiverAccount);
			return true;
		}
		return false;
	}

	@Override
	public List<TransactionDetails> getTransactions(TransactionDetailsByDate transacByDate) {
		Integer userId = transacByDate.getUserId();
		String transactionUser = userService.getUserById(userId).getEmailId();
		String authenticatedUser = accountService.getAuthentication().getName();
//		checking transactionUser has right authority to get the data
		if (!transactionUser.equals(authenticatedUser)) {
			throw new AuthenticationFailedException("you don't have authority to access data with these userId!...");
		}
		Long account_number = accountService.getAccountByUserId(userId).getAccountNumber();
		List<TransactionDetails> listOfTransactions = transactionDetailRepo.findAll();
		listOfTransactions = listOfTransactions.stream().filter(transactionByNumber -> {
//			filter By Account Number
			if (transactionByNumber.getSenderAccountNumber().equals(account_number)
					|| transactionByNumber.getReceiverAccountNumber().equals(account_number)) {
				return true;
			}
			return false;
		}).filter(transactionByDate -> {
//			filter By Date
			return true;
		}).collect(Collectors.toList());
		return listOfTransactions;
	}

}
