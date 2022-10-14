package com.abc.bankingApp.properties;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateOfBirthValidate implements ConstraintValidator<DateOfBirthValidator,LocalDate> {

	int lower;
	int upper;
	String message;
	
	@Override
	public void initialize(DateOfBirthValidator constraints) {
		lower=constraints.lower();
		upper=constraints.upper();
		message=constraints.message();
	}
	
	
	@Override
	public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
		int years = Period.between(dateOfBirth, LocalDate.now()).getYears();		
		if(years>lower && years<upper)
			return true;
		else
			return false;
	}

	
}
