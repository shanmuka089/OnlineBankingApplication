package com.abc.bankingApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abc.bankingApp.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	@Query(value= "select * from User u where u.email_id=:emailId",nativeQuery = true)
	Optional<User> findByEmailId(String emailId);
}
