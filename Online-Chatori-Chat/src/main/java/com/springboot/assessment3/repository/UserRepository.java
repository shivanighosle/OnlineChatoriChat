package com.springboot.assessment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.assessment3.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByPassword(String password);
	
	public User findById(long id);
	
	public User findByEmailAndPasswordAndOtp(String email, String password, long otp);
}
