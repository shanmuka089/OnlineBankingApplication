package com.abc.bankingApp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Transaction_Details")
@ToString
public class TransactionDetails {

	@Id
	@GenericGenerator(name = "TransactionIdGenerator",strategy = "com.abc.bankingApp.properties.TransactionIdGenerator")
	@GeneratedValue(generator = "TransactionIdGenerator")
	private Long transactionId;
	private LocalDateTime transactionDate;
	private Long senderAccountNumber;
	@NotNull(message = "receiver account number cannot be empty")
	private Long receiverAccountNumber;
	@NotNull(message ="please enter a valid amount" )
	private Double amount;
	@NotNull(message = "please add remarks")
	private String remarks;
}
