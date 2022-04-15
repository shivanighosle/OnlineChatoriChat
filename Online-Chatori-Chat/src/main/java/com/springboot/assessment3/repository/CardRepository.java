package com.springboot.assessment3.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.assessment3.model.*;

@Repository
public interface CardRepository extends JpaRepository<Card , Long> {

	void save(List<Dish> dish);
	
	public Card findByDishid(long dishid);
	
	public List<Card> findByUserId(long userId);
	
	public List<Card> findByRestorentId(long restorentId);

}
