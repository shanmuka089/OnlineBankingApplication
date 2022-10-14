package com.abc.bankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.bankingApp.model.AccountDetails;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long>{

}
