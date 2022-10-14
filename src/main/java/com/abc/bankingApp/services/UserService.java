package com.abc.bankingApp.services;

import com.abc.bankingApp.model.User;
import com.abc.bankingApp.model.UserCredentials;

public interface UserService {
	UserCredentials saveUser(User user);
	Boolean updateUser(User user);
	User getUserById(Integer id);
	Boolean updatePassword(UserCredentials credentials);
	Long accountInstanceId();
	User getUserByEmail(String name);
}
