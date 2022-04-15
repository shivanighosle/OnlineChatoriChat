package com.springboot.assessment3.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.assessment3.model.Dish;


public interface DishRepository extends JpaRepository<Dish, Long>{

	//pagination....................
	@Query("from Dish as r where r.restorent.RestorentId=:RestorentId") 
	//public List<Dish> findDishByRestorent(@Param("RestorentId")long RestorentId); 
	public List<Dish> findDishByRestorent(@Param("RestorentId")long RestorentId); 
	
	public Dish findByDishid(@Param("dishid")long dishid);  
}
