package com.springboot.assessment3.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Card;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.assessment3.model.Buy;
import com.springboot.assessment3.model.Client;
import com.springboot.assessment3.model.Payment;
import com.springboot.assessment3.model.Restorent;
import com.springboot.assessment3.repository.BuyRepository;
import com.springboot.assessment3.repository.CardRepository;
import com.springboot.assessment3.repository.PaymentRepository;
import com.springboot.assessment3.repository.RestorentRepository;

@Controller
public class PriceController {

	@Autowired private CardRepository cardRepository;
	@Autowired private BuyRepository buyRepository;
    @Autowired private PaymentRepository paymentRepository;
	@Autowired private RestorentRepository restorentRepository;
	
	@GetMapping("/pricecard")
	public String price() {
		return "pricecard";
	}
	
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String,Object> data,HttpServletRequest request,Client client) throws RazorpayException {
		System.out.println(data);
		int amt = Integer.parseInt(data.get("amount").toString());
		
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_Sp0TxZ70PsKMQg","hM6satmKx1iEZxa3TezeCJuW");
		JSONObject obj  =  new JSONObject();
		obj.put("amount", amt*100);
		obj.put("currency", "INR");
		obj.put("receipt", "txn_235425");
		
		//creating new+ order
		Order order  = razorpayClient.Orders.create(obj);
		System.out.println(order);
		
		HttpSession session = request.getSession(false);
		long clientid = (long)session.getAttribute("clientId");
		
		//save orderin database 
		Payment payment = new Payment();
		payment.setAmount(order.get("amount")+"");
		payment.setOrderId(order.get("id"));
		payment.setStatus("created");
		payment.setReciept(order.get("receipt"));
		payment.setClientid(clientid);
		
		//getting current date 
		SimpleDateFormat simpledateformate = new SimpleDateFormat("dd/MM/yyyy ");
		Date date = new Date();
		payment.setCurrentdate(simpledateformate.format(date));

		//getting expire date 
		Calendar cal = Calendar.getInstance();		
		cal.add(Calendar.MONTH, + 1);		
		Date expirydate = cal.getTime();
		payment.setExpiredate(simpledateformate.format(expirydate));


        if(restorentRepository.findByClientId(clientid) != null) {
        	System.out.println("already have a client");
        	
        	Restorent restorent = restorentRepository.findByClientId(clientid);
            System.out.println("client id:- "+restorent.getClientId());
        	
            Payment payment1 = paymentRepository.findByClientid(clientid);
        	
        	payment1.setCurrentdate(simpledateformate.format(date));
        	payment1.setExpiredate(simpledateformate.format(expirydate));
        	
        	List<Payment> payment2 = new ArrayList<Payment>();
        	paymentRepository.save(payment1);
        	payment2.add(payment1);
        	payment2.get(0).getStatus();
        	
        	return "done";

        }else {
        	System.out.println("new Client");
        	this.paymentRepository.save(payment);
        	
        	List<Payment> payment2 = new ArrayList<Payment>();
        	 payment2.add(payment);
        	 System.out.println(payment2.get(0).getStatus());
        	 
        	 return "created";
        }
        
	}
	
/*------------------------------------- creating payment api for buyer ------------------------------------------- */
	@PostMapping("/buyer")
	@ResponseBody
	public String createPaymentOrder(@RequestBody Map<String,Object> data, Principal principal,@ModelAttribute("card")Card card,HttpServletRequest request) throws RazorpayException {
		System.out.println(data);
		int amt = Integer.parseInt(data.get("amount").toString());
		
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_Sp0TxZ70PsKMQg","hM6satmKx1iEZxa3TezeCJuW");
		JSONObject obj  =  new JSONObject();
		obj.put("amount", amt*100);
		obj.put("currency", "INR");
		obj.put("receipt", "txn_235425");

		System.out.println("payment started...");
		//creating new+ order
		Order order  = razorpayClient.Orders.create(obj);
		System.out.println(order);		
	
		HttpSession session = request.getSession(false);
		long userId = (long) session.getAttribute("id");
       
		SimpleDateFormat simpledateformate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, +2);
		Date orderdatetime = cal.getTime();
		
		Buy buy = new Buy();
		buy.setTotalprice(order.get("amount")+"");
		buy.setOrderid(order.get("id")+"");
		buy.setReciept(order.get("receipt"));
		buy.setUserId(userId);
		buy.setStatus("placed");
		buy.setOrderdatetime(simpledateformate.format(orderdatetime));
		
		
		System.out.println("saving data into db process has been started........");
		buyRepository.save(buy);	
		System.out.println("saving data into db process has been done........");

		cardRepository.deleteAll();
		//payment.setAmount(order.get("amount")); 
		return order.toString();

	}
	 
}
