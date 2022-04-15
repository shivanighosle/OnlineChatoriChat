package com.springboot.assessment3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.assessment3.repository.ClientRepository;
import com.springboot.assessment3.repository.DishRepository;
import com.springboot.assessment3.repository.PaymentRepository;
import com.springboot.assessment3.repository.RestorentRepository;
import com.springboot.assessment3.model.*;


//to achieve database level user login validation, we need to overwrite the UserDetailsService class
@Service
public class ClientService  {
  
	@Autowired private ClientRepository clientRepository;
	@Autowired private RestorentRepository restorentRepository;
	@Autowired private DishRepository dishRepository;
	@Autowired private PaymentRepository paymentRepository;
	
	public List<Client> getAllClient(){
		List<Client> list = (List<Client>) clientRepository.findAll();
		return list;
	}
	
//	@Transactional
	public Client addClient(Client client) {
		Client result = clientRepository.save(client);
		return result;
	}
	
	public Client getClientById(long id) {
		 Client client = clientRepository.findById(id);
		 return client ;
	}
	
	public void updateClient(Client client,long id) {
		client.setClientid(id);
		clientRepository.save(client);
	}
	
	//varifying login cretidential 
	 public Client clientLoginAuth(String email, String password, long otp) {
		Client authclient = clientRepository.findByEmailAndPasswordAndOtp(email, password, otp);	 
		return authclient;
    }
		 
	//adding information of restorent into db 
	public Restorent addRestorent(Restorent restorent) {	
		Restorent result = restorentRepository.save(restorent);
		return result;
	}
	
	//adding dish  
	public Dish addDish(Dish dish) {
		Dish result = dishRepository.save(dish);
		return result;
	}
	
	//client profile login authentication 
	public Client ClientProfileLoginAuth(String email,String password,long id) {
		Client clientloginauth = clientRepository.findByEmailAndPasswordAndClientid(email,password,id);
		return clientloginauth;
	}
	
	//geting client restorentId 
	public Restorent GetRestorentId(long clientId) {
		Restorent result = restorentRepository.findByClientId(clientId); 
	    return result;
	}
	
	//getting my plan details 
	public Payment getMyPlanDetail(long clientid) {
		Payment result = paymentRepository.findByClientid(clientid);
		return result;
	}
}
