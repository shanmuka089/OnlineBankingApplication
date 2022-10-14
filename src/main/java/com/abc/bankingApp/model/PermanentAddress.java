package com.abc.bankingApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Setter
@Getter
@Table(name = "Permanent_Address")
public class PermanentAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String doorNum;
	private String street;
	private String area;
	private String city;
	private String stateCode;
	private Integer pincode;
	
}
