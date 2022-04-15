package com.springboot.assessment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.assessment3.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{

	@Query(value="select * from membership m where m.clientid = :clientid",nativeQuery=true)
	public Payment findByClientid(long clientid);
}
