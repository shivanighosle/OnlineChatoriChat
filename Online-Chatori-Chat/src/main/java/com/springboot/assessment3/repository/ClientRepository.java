package com.springboot.assessment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.assessment3.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	 public  Client findById(long clientid);

	 public  Client getClientByFullName(String fullName); 
	
	 public  Client findByEmailAndPasswordAndOtp(String email, String password, long otp);
     
	 public  Client findByEmailAndPasswordAndClientid(String email,String password,long clientid);
	// public  Client findByEmailAndPassword(String email, String password);
}
