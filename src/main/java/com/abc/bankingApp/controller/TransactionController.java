package com.abc.bankingApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.bankingApp.common.TransactionFailedException;
import com.abc.bankingApp.model.TransactionDetails;
import com.abc.bankingApp.model.TransactionDetailsByDate;
import com.abc.bankingApp.services.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private Logger logger=LogManager.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionService transactionService;

	@PostMapping("/create")
	public ResponseEntity<String> doTransaction(@RequestBody TransactionDetails details) {
		logger.debug("doing the transaction inside doTransaction method");
		Boolean transaction = transactionService.doTransaction(details);
		if (!transaction) {
			logger.error("error occured while doing transaction");
			throw new TransactionFailedException("Transaction has been failed!");
		}
		return new ResponseEntity<String>("Transaction Successfull!..", HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<TransactionDetails>> getTransaction(@PathVariable("userId") Integer id,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		logger.info("getting transaction details of user:"+id+" from:"+fromDate+" to:"+toDate);
		TransactionDetailsByDate detailsByDate=new TransactionDetailsByDate();
		detailsByDate.setUserId(id);
		detailsByDate.setFromDate(LocalDate.parse(fromDate));
		detailsByDate.setToDate(LocalDate.parse(toDate));
		logger.debug("getting the transactions by calling getTransactions method");
		List<TransactionDetails> transactions = transactionService.getTransactions(detailsByDate);
		return new ResponseEntity<List<TransactionDetails>>(transactions,HttpStatus.OK);
	}

}
