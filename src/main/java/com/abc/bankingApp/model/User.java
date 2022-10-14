 package com.abc.bankingApp.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import com.abc.bankingApp.properties.DateOfBirthValidator;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "User")
@ToString
public class User {

	@Id
	@GenericGenerator(name = "userIdGenerator",strategy = "com.abc.bankingApp.properties.UserIdGenerator")
	@GeneratedValue(generator = "userIdGenerator")
	private Integer userId;
	
	private String firstName="";
	
	@NotEmpty
	@Pattern(regexp = "[a-z | A-Z]+'?[a-z | A-Z]*",message = "name should not contain any special character except(')")
	@NotNull(message = "last name required")
	private String lastName;
	
	@DateOfBirthValidator(lower = 18,upper = 60,message = "age should between 18 & 60")
	@NotNull(message = "date of birth required")
	private LocalDate dateOfBirth;
	
	@NotNull(message = "gender required")
	private Character gender;
	
	@NotNull(message = "email id required")
	private String emailId;
	
	@Transient
	@NotNull(message = "password cannot be required")
	private String password;
	
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}",message = "please enter valid pan number")
	@NotNull(message = "pan number required")
	private String panNumber;
	
	@Pattern(regexp = "[7-9]{1}[0-9]{9}",message = "please enter valid phone number")
	@NotNull(message = "phone number required")
	private String phoneNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "currentAddressId",referencedColumnName = "id")
	private CurrentAddress currentAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "permanentAddressId",referencedColumnName = "id")
	private PermanentAddress permanentAddress;
	
	private Long accountNumber;
	
}
