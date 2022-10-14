package com.abc.bankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.bankingApp.model.TransactionDetails;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetails, Long>{

}
