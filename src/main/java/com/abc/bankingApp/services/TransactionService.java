package com.abc.bankingApp.services;

import java.util.List;

import com.abc.bankingApp.model.TransactionDetails;
import com.abc.bankingApp.model.TransactionDetailsByDate;

public interface TransactionService {

	Boolean doTransaction(TransactionDetails details);
	List<TransactionDetails> getTransactions(TransactionDetailsByDate byDate);
}
