package com.abc.bankingApp.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionDetailsByDate {

	private Integer userId;
	private LocalDate fromDate;
	private LocalDate toDate;
}
