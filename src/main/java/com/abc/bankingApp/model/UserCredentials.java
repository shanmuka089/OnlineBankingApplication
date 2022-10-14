package com.abc.bankingApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "User_Credintials")
public class UserCredentials {

	
	@Id
	private String emailId;
	private String password;
	
}
