package com.abc.bankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.bankingApp.model.UserCredentials;

public interface UserCredentialRepository extends JpaRepository<UserCredentials,String>{

}
