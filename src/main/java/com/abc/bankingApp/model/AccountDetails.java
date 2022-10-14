package com.abc.bankingApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;


@Data
@Table(name = "Account_Details")
@Entity
@ToString
public class AccountDetails {

	@Id
	private Long accountNumber;
	private String accountHolderName;
	private Double currentBalance;
}
