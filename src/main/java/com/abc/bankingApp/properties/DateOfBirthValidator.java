package com.abc.bankingApp.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DateOfBirthValidate.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirthValidator {
	
	String message() default "age should be greater than {lower}-{upper}!..";
	
	int lower() default 18;
	int upper() default 100;
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default { };

}
