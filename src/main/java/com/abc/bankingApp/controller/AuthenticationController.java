package com.abc.bankingApp.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abc.bankingApp.common.AuthenticationFailedException;
import com.abc.bankingApp.common.InvalidOtpException;
import com.abc.bankingApp.model.AuthenticationRequest;
import com.abc.bankingApp.model.OtpRequest;
import com.abc.bankingApp.model.ResponseToken;
import com.abc.bankingApp.model.User;
import com.abc.bankingApp.properties.JwtUtil;
import com.abc.bankingApp.services.UserService;

@RestController
public class AuthenticationController {

	private Logger logger=LogManager.getLogger(AuthenticationController.class);
	
	private Map<Long, Authentication> authentications = new HashMap<>();
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<Long> loginUser(@RequestBody AuthenticationRequest request) {
		Authentication auth = null;
		try {
			logger.debug("inside loginUser method calling authenticate method of authentiactionManager");
			auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
		} catch (Exception e) {
			logger.error("while authenticating the request got an error");
			throw new AuthenticationFailedException("invalid email or password!..");
		}
		logger.debug("calling accountInstanceId method for instanceId generation");
		Long instanceId = userService.accountInstanceId();
		logger.debug("put the values of instance id & authentication instance into the map");
		authentications.put(instanceId, auth);
		return new ResponseEntity<Long>(instanceId, HttpStatus.ACCEPTED);
	}

	
	@PostMapping("/authenticate")
	public ResponseEntity<ResponseToken> authenticateRequest(@RequestBody OtpRequest request) {
		logger.debug("getting authentication instance inside a authenticateRequest method");
		Authentication authentication = authentications.get(request.getAuthenticateInstanceId());
		
		if (authentication == null) {
			logger.error("error occured while getting authentication instance with instance id");
//			it is predefined exception class & it can be handle by default exception handler with 403 status code
			throw new AuthenticationCredentialsNotFoundException("these request is not authenticated!..");
		}
		Integer defaultOtpCode = 123456;
		if (!defaultOtpCode.equals(request.getOtpCode())) {
			logger.error("error occured because of user entered otp");
			throw new InvalidOtpException("you entered invalid OTP,please try with valid one!..");
		}
		
		String name = authentication.getName();
		logger.info("getting the User instance of "+name);
		User user = userService.getUserByEmail(name);
		logger.debug("calling the generate token method by passing the authentication instance");
		String generatedToken = jwtUtil.generateToken(authentication);
		
		ResponseToken token=new ResponseToken();
		token.setToken(generatedToken);
		token.setTokenType("Bearer");
		token.setUserId(user.getUserId());
		
		return new ResponseEntity<ResponseToken>(token,HttpStatus.OK);
	}

}
