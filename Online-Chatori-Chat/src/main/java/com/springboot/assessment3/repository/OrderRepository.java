package com.springboot.assessment3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.assessment3.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {

	@Query("select o from Orders o where o.userId = :userId")
	public List<Orders> findByUserId(long userId);
	
}
