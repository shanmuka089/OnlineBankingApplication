package com.abc.bankingApp.services;

import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abc.bankingApp.common.AuthenticationFailedException;
import com.abc.bankingApp.common.UserAlreadyExistException;
import com.abc.bankingApp.common.UserCredentialException;
import com.abc.bankingApp.common.UserNotFoundException;
import com.abc.bankingApp.model.AccountDetails;
import com.abc.bankingApp.model.User;
import com.abc.bankingApp.model.UserCredentials;
import com.abc.bankingApp.repository.UserCredentialRepository;
import com.abc.bankingApp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserCredentialRepository credentialRepo;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	@Transactional
	public UserCredentials saveUser(User user) {
		Optional<UserCredentials> userByEmailId = credentialRepo.findById(user.getEmailId());		
		if(!userByEmailId.isEmpty()) {
			throw new UserAlreadyExistException("user already found with given id!");
		}
		Long accountnumber=generateAccountNumber(user);
		AccountDetails account=new AccountDetails();
		account.setAccountNumber(accountnumber);
		String holderName = user.getFirstName().concat(" ").concat(user.getLastName());
		account.setAccountHolderName(holderName.trim());
		account.setCurrentBalance(1000.0);
		
		user.setAccountNumber(accountnumber);
		User save = userRepo.save(user);
		
		if(save!=null) {
			UserCredentials credentials=new UserCredentials();
			credentials.setEmailId(save.getEmailId());
			String encodedPassword=encoder.encode(save.getPassword());
			credentials.setPassword(encodedPassword);
			accountService.saveAccountSummary(account);	
			UserCredentials userCredentials = credentialRepo.save(credentials);
			return userCredentials;
		}
		return null;
	}
	
	
	private Long generateAccountNumber(User user) {
		Random random=new Random();
		Long accountNumber=1000000000+random.nextLong(100000000, 999999999);
		if(accountService.getAccountByAccountNumber(accountNumber)!=null) {
			accountNumber=generateAccountNumber(user);
		}
		return accountNumber;
	}
	
	@Override
	public Boolean updateUser(User user) {
		if(!userRepo.existsById(user.getUserId())) {
			throw new UserNotFoundException("you are trying to update for not existed user!");
		}
		if(userRepo.save(user)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserById(Integer id) {
		String transactionUser = userRepo.findById(id).get().getEmailId();
		String authenticatedUser = accountService.getAuthentication().getName();
		
//		checking User has right authority to get the data
		if(!transactionUser.equals(authenticatedUser)) {
			throw new AuthenticationFailedException("you don't have authority to access data with these userId!...");
		}
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not exist with given id!");
		}
		return optionalUser.get();
	}

	@Override
	public Boolean updatePassword(UserCredentials credentials) {
		Optional<UserCredentials> optionalCredential = credentialRepo.findById(credentials.getEmailId());
		if(optionalCredential.isEmpty()) {
			throw new UserCredentialException("User credentials not exist with given email Id!");
		}
		UserCredentials userCredentials = optionalCredential.get();
		String encodedPassword = encoder.encode(credentials.getPassword());
		userCredentials.setPassword(encodedPassword);
		UserCredentials savedCredentials = credentialRepo.save(userCredentials);
		if(savedCredentials!=null) {
			return true;
		}
		return false;
	}

	@Override
	public Long accountInstanceId() {
		Random random = new Random();
		long otp = 1000000 + random.nextLong(900000);
		return otp;
	}

	@Override
	public User getUserByEmail(String name) {
		Optional<User> user = userRepo.findByEmailId(name);
		return user.get();
	}
}
