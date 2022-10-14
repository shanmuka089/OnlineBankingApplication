package com.abc.bankingApp.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abc.bankingApp.model.UserCredentials;
import com.abc.bankingApp.repository.UserCredentialRepository;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private UserCredentialRepository credentialRepo;

	@Override
	public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
		UserCredentials userCredentials = credentialRepo
		.findById(userMail)
		.orElseThrow(()->new UsernameNotFoundException("user not found with givenUserName"));
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return User.withUsername(userCredentials.getEmailId()).password(userCredentials.getPassword()).authorities(authorities).build();
	}

}
