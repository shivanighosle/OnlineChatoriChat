package com.springboot.assessment3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.assessment3.model.Buy;
import com.springboot.assessment3.model.Card;
import com.springboot.assessment3.model.Dish;
import com.springboot.assessment3.model.Orders;
import com.springboot.assessment3.model.Restorent;
import com.springboot.assessment3.model.User;
import com.springboot.assessment3.repository.BuyRepository;
import com.springboot.assessment3.repository.CardRepository;
import com.springboot.assessment3.repository.DishRepository;
import com.springboot.assessment3.repository.OrderRepository;
import com.springboot.assessment3.repository.RestorentRepository;
import com.springboot.assessment3.repository.UserRepository;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	@Autowired private RestorentRepository restorentRepository;
	@Autowired private DishRepository dishRepository;
	@Autowired private CardRepository cardRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private BuyRepository buyRepository;
	
	public User addUser(User user) {
		User result = userRepository.save(user);	
		return result;
	}
	//user login validation
	public User userLogin(String email, String password, long otp) {
		User auth = userRepository.findByEmailAndPasswordAndOtp(email, password, otp);
		return auth;
	}
	
	//get the list of shops
	public List<Restorent> getAllRestorents(){
		List<Restorent> list = restorentRepository.findAll();	
     	return list;
	 }
	
	//get restorent by keyword 
	public List<Restorent> getByKeyword(String keyword) {
		return restorentRepository.findByKeyword(keyword);
	}

		
	//get dish by dishId 
	public Dish getById(long id) {
		return dishRepository.findByDishid(id);
	}
	
	//getalldetail of card 
	 public List<Card> getAllDishes(long userId){
		 List<Card> cards = cardRepository.findByUserId(userId);
		 return cards;
	 }
	 
	 //get all data for notification 
	 public List<Orders> getAllNotification(long userId){
		List<Orders> order = orderRepository.findByUserId(userId);
		return order;
	 }
	 
	 //getting all dishes for one restorent 
	 public List<Dish> getAllDishesFromRestorent(long restorentid){
		 List<Dish> dishes = dishRepository.findDishByRestorent(restorentid);
		 return dishes;
	 }
	 
	 //deleting user account 
	 public void deleteUserAccount(long userid ) {
		 userRepository.deleteById(userid);
	 }
}
