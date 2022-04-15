package com.springboot.assessment3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.assessment3.model.Buy;

@Repository
public interface BuyRepository extends JpaRepository<Buy,Long>{

	public Buy findByOrderid(String orderid); 
	
	public List<Buy> findByUserId(long userid);
}
