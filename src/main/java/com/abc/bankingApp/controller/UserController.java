package com.abc.bankingApp.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.bankingApp.common.UpdationFailedException;
import com.abc.bankingApp.model.ErrorResponse;
import com.abc.bankingApp.model.StatusResponse;
import com.abc.bankingApp.model.User;
import com.abc.bankingApp.model.UserCredentials;
import com.abc.bankingApp.services.UserService;

@RestController
@RequestMapping("/profile")
public class UserController {
	
	Logger logger=LogManager.getLogger(UserController.class);
	
	@Autowired(required = true)
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<StatusResponse> createUser(@Valid @RequestBody User user,BindingResult set){
		StatusResponse statusresponse=new StatusResponse();
		if(set.hasErrors()) {
			logger.error(set.getAllErrors());
			statusresponse.setStatus("Error");
			ErrorResponse response=new ErrorResponse();
			response.setMessage(set.getAllErrors().toString());
			statusresponse.setErrors(response);
			return new ResponseEntity<StatusResponse>(statusresponse,HttpStatus.BAD_REQUEST);
		}
		UserCredentials credentials = userService.saveUser(user);
		statusresponse.setStatus("Success");
		statusresponse.setData(credentials);
		return new ResponseEntity<StatusResponse>(statusresponse,HttpStatus.CREATED);
	}
	@PostMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody User user){
		Boolean updateUser = userService.updateUser(user);
		if(updateUser) {
			return new ResponseEntity<String>("User details updated Successfull!..",HttpStatus.OK);
		}
		throw new UpdationFailedException("Error occured while updating User!..");
	}
	
	@PostMapping("/password/update")
	public ResponseEntity<String> updatePassword(@RequestBody UserCredentials credentials){
		Boolean updatePassword = userService.updatePassword(credentials);
		if(updatePassword) {
			return new ResponseEntity<String>("Password updated successfull!..",HttpStatus.OK);
		}
		throw new UpdationFailedException("Error occured while updating password!..");
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer id){
		User user = userService.getUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
