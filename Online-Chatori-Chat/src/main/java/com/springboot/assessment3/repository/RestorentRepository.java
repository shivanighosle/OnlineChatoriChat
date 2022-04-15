package com.springboot.assessment3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.assessment3.model.Restorent;


@Repository
public interface RestorentRepository extends JpaRepository<Restorent, Long> {

	public Restorent findById(long RestorentId);
	
	List<Restorent> findByName(String name);

    public Restorent getRestorentByName(String name);
    
    public Restorent findByClientId(long clientId);
    
   
    
    //search query 
    @Query(value="select * from restorent r where r.name like %:keyword%" , nativeQuery = true )
    List<Restorent> findByKeyword(@Param("keyword") String keyword);
}
