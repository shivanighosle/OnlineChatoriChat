package com.springboot.assessment3.controller;

import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.assessment3.model.Client;
import com.springboot.assessment3.model.Restorent;
import com.springboot.assessment3.repository.ClientRepository;
import com.springboot.assessment3.service.ClientService;
import com.springboot.assessment3.service.EmailService;

@Controller
public class VarifyOtpController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	private ClientService clientService;
    
    @GetMapping("/DashBoard")
    public String DashBoard() {
    	return "dashboard";
    }
 /*---------------------------------Client login for profile----------------------------------------------*/ 
  @GetMapping(value="/ClientProfileLogin")
  public String ClientProfileLogin(Model model) {	  
	  model.addAttribute("clientprofile", new Client());
	  System.out.println("profile login initiated");
	  return"ClientProfileLogin";
  }
  
  /*---------------------------------client logout for profil validation -----------------------------------------*/
   @PostMapping(value="ClientProfileLoginAuth")
   public String ClientProfileLoginAuth(@ModelAttribute("clientprofile") Client client,Model model,HttpServletRequest request) {
	   
	  
	   
	   if(clientService.ClientProfileLoginAuth(client.getEmail(),client.getPassword(), client.getClientid()) !=null) {
		   Client clientloginauth = clientService.ClientProfileLoginAuth(client.getEmail(),client.getPassword(), client.getClientid());
			 
		   long clientId = clientloginauth.getClientid();  
		   Restorent restorent = clientService.GetRestorentId(clientId);
		   
		   HttpSession session = request.getSession(true);
		   request.getSession().setAttribute("clientId", restorent.getClientId());
		   
		   model.addAttribute("restorent",restorent);
		   model.addAttribute("profilesuccessmessage","Welcome back querida");
		   
		   System.out.println("successfully login in");
		   return "CProfile";
	   }else {
		   System.out.println("Unable to  login in");
		   model.addAttribute("message2","Something went wrogn please check your credentials and try again");
	
		   return "ClientProfileLogin";
	   }
   }
    //saving the data of signup page into db with otp it also send mail to user
    @PostMapping("/SaveClient")
    public String saveClient(@ModelAttribute("client") Client c, 
    		                 @RequestParam("email") String email, Model model) {
		
		    // generation 4 digit otp
			System.out.println("Email:- "+email);
			
			Random random = new Random(1000);
			long otp = random.nextInt(9999999);		
			
			System.out.println("OTP: "+otp);
			
			c.setOtp(otp);
			clientService.addClient(c);
			
			String to      = email;
			String subject = "OTP From SCM";
			String message = "OTP for account varification :- "+otp+" ";
			//String meassage = "OTP for account varification = "+otp+" ";
			
		boolean flag =	emailService.sendEmail(subject, message, to);
		System.out.println(flag);
			
		if (flag) {	
			model.addAttribute("message","You Have Successfully Done Your Registration");
			return "ClientLogin";//varifyOtp page basically this is 
		}else {
			model.addAttribute("clientsideerrormessage","Oop's Something Went Wrogn, Check Data You Have Entered");
			System.out.println("not done...............");			
			return "ClientSignUp";	
	  }    
  }
	
  @RequestMapping(value = {"/clientlogout"}) 
  public String logoutDo(HttpServletRequest request,HttpServletResponse response,Model model) {
	HttpSession session = request.getSession(false);
	
	if(session != null) 
		session.invalidate();
	   model.addAttribute("logout","You have Successfully LogOut");
		return "home"; 
  }
	 
}
