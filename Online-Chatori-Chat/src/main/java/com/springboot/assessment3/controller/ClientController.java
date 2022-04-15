package com.springboot.assessment3.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.assessment3.model.Buy;
import com.springboot.assessment3.model.Card;
import com.springboot.assessment3.model.Client;
import com.springboot.assessment3.model.Dish;
import com.springboot.assessment3.model.Orders;
import com.springboot.assessment3.model.Payment;
import com.springboot.assessment3.model.Restorent;
import com.springboot.assessment3.model.User;
import com.springboot.assessment3.repository.BuyRepository;
import com.springboot.assessment3.repository.CardRepository;
import com.springboot.assessment3.repository.ClientRepository;
import com.springboot.assessment3.repository.DishRepository;
import com.springboot.assessment3.repository.OrderRepository;
import com.springboot.assessment3.repository.PaymentRepository;
import com.springboot.assessment3.repository.RestorentRepository;
import com.springboot.assessment3.repository.UserRepository;
import com.springboot.assessment3.service.ClientService;

@Controller
public class ClientController {

	@Autowired private ClientRepository clientRepository;
	@Autowired private RestorentRepository restorentRepository;
	@Autowired private ClientService clientService;
	@Autowired private DishRepository dishRepository;
	@Autowired private BuyRepository buyRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private PaymentRepository paymentRepository;
	@Autowired private CardRepository cardRepository;
/*----------------------- Main page  --------------------------------------*/
	@GetMapping("/home")
	public String homePage(Model model) {
		return "home";
	}

/*----------------------- calling client-home page --------------------------------------*/
	// just for testing purposes only
	@GetMapping("/dashboard")
	public String homePage1(Model model) {
		return "dashboard";
	}

/*----------------------- getting the detail's of all the register clients --------------------------------------*/
	@GetMapping("/AllClient")
	public String viewLoginPage(Model model) {
		List<Client> clients = clientService.getAllClient();
		model.addAttribute("clients", clients);
		System.out.println(clients);
		return "new";
	}

	
/*----------------------- calling client-sign-up page --------------------------------------*/
	@GetMapping("/ClientSignUp")
	public String add(Model model) {
		model.addAttribute("client", new Client());
		return "ClientSignUp";
	}

/*----------------------- calling client-login page for login  --------------------------------------*/
	@GetMapping(value="ClientLoginAuth")
	public String clientLoginValidation(Model model) {
		model.addAttribute("client", new Client());
		return "ClientLogin";
	}


/*----------------------- calling client-login page for authentication  --------------------------------------*/
	@PostMapping(value="/ClientLoginAuth")
	public String clinetLogin(@ModelAttribute("client") Client client,Model model,HttpServletRequest request) {
				
		if(clientService.clientLoginAuth(client.getEmail(),client.getPassword(),client.getOtp()) != null) {
		
			Client authclient = clientService.clientLoginAuth(client.getEmail(),client.getPassword(),client.getOtp());
			long id = authclient.getClientid();
			
			HttpSession session = request.getSession(true);
			request.getSession().setAttribute("clientId",id);
			
			
			model.addAttribute("client", authclient);
			System.out.println("client authentication successfull");
			model.addAttribute("pricecardsuccessmessage","you have successfully login, Now Choose below plan to continue");
			return "pricecard";
		}else {
			System.out.println("client authentication failed");
			model.addAttribute("message1","Something went wrogn please check your credentials and try again");		    
		  return"ClientLogin";
		}
	}

/*----------------------- calling add restorent page --------------------------------------*/
	@GetMapping("/addRestro")
	public String addRestorent1(Model model,HttpServletRequest request) {
		model.addAttribute("restorent", new Restorent());		
		HttpSession session = request.getSession(false);
			
		return "addRestro";// this will become addRestro.html
	}

/*-----------------------  saving the details of restorent  --------------------------------------*/
	@PostMapping("/SaveRestorent")
	public String saveRestorent(@ModelAttribute("restorent") Restorent restorent,HttpServletRequest request) {      
	
		HttpSession session = request.getSession(false); 
		long userId = (long)session.getAttribute("clientId");
		
		restorent.setClientId(userId);		
		clientService.addRestorent(restorent);
		return "CProfile";
	}

	
/*-----------------------  client home page  --------------------------------------*/
	@GetMapping("/CProfile/{RestorentId}")
	public String showCProfile(Model model,@PathVariable long RestorentId) {
		Restorent restorent = restorentRepository.findById(RestorentId);		
		model.addAttribute("restorent", restorent);
		return "CProfile";
	}

/*---------------------------------- opening edit restorent page     --------------------------------------*/
	@GetMapping("/editRestro/{RestorentId}")
	public String EditRestorent(@PathVariable long RestorentId, Model model) {
		Restorent restorent = restorentRepository.findById(RestorentId);
		model.addAttribute("restorent", restorent);
		return "EditRestorent"; // EditRestorent.html
	}
	
/*-----------------------  saving the details of restorent after edit  --------------------------------------*/
	@PostMapping("/EditRestorent/{RestorentId}")
	public String editRestro(@PathVariable long RestorentId, Model model,
			                 @ModelAttribute("restorent") Restorent restorent,HttpServletRequest request) {
	
		HttpSession session = request.getSession(false);
		long clientId = (long)session.getAttribute("clientId");
		restorent.setClientId(clientId);
		restorentRepository.save(restorent);
		model.addAttribute("restorent", restorentRepository.findById(restorent.getRestorentId()));	
		return "CProfile";
	}


/*-----------------------  showing dish form for adding new dishes  --------------------------------------*/
	@GetMapping("/dish/{RestorentId}")
	public String dish(Model model,@PathVariable long RestorentId) {
		
		Restorent restorent = restorentRepository.findById(RestorentId);
		
		model.addAttribute("restorent",restorent);
		model.addAttribute("dish", new Dish());		
		return "adddish";
	}


/*-----------------------  saving the detail of recently added dishes  --------------------------------------*/	
	@PostMapping("/SaveDish/{RestorentId}")
	public String SaveDish(@ModelAttribute("dish") Dish dish, Model model, 
			               @PathVariable long RestorentId,
			               @ModelAttribute("restorent") Restorent restorent) {	
		
		model.addAttribute("restorent", restorentRepository.findById(restorent.getRestorentId()));
		dish.setRestorent(restorent);		
		clientService.addDish(dish);
		
		model.addAttribute("dishsuccessmessage","dish successfully added");
		return "redirect:/showdishes/"+RestorentId;
	}

/*-----------------------  showing dishes  --------------------------------------*/
	@GetMapping("/showdishes/{RestorentId}")
	public String show(Model model,  Dish dish,
			           @ModelAttribute("restorent") Restorent restorent) {		
		List<Dish> dishes = this.dishRepository.findDishByRestorent(restorent.getRestorentId());
	
		model.addAttribute("restorent",restorent);
		model.addAttribute("dishes", dishes);
		
		return"showdishes";
	}
	
/*------------------------------- delete dish--------------------------------------------*/
  @GetMapping("/deletedish")
  public String deletedish(@RequestParam long id, Model model, HttpServletRequest request) {	  
	  dishRepository.deleteById(id);
	  HttpSession session = request.getSession(false);
		long clientId = (long) session.getAttribute("clientId");
	
		Restorent restorent = clientService.GetRestorentId(clientId);
		long restroid = restorent.getRestorentId();
		System.out.println("restorent id:- "+restroid);
		
		model.addAttribute("restorent",restorent);
		model.addAttribute("deletedishmessage","dish deleted");
	  return "redirect:/showdishes/"+restroid;
  }
  
/*------------------------- view order's---------------------------*/
	@GetMapping(value="/ViewOrder")
	public String GetAllOrder(Model model, HttpServletRequest request) {
		System.out.println("view all orders:-");
		
		HttpSession session = request.getSession(false);
		long clientId = (long) session.getAttribute("clientId");
	
		Restorent restorent = clientService.GetRestorentId(clientId);
		
		long restorentId = (long) restorent.getRestorentId();
		
//		List<Card> cards = cardRepository.findByRestorentId(restorentId);
//		
//		List<Buy> buy = new ArrayList<Buy>();
//		for(Card c:cards) {
//			if(c.getRestorentId() == restorentId) {				
//			buy.addAll(buyRepository.findByUserId(c.getUserId()));
//			}
//		}
		
		System.out.println("current restorentId: " + restorentId);
		List<Buy> buyer = buyRepository.findAll();
		System.out.println(buyer);
		
	    List<String> list = new ArrayList<String>();
		
	    for(Buy by:buyer) {
			String status = "placed";
			if(status.equals(by.getStatus())) {
				list.add(status);
			}
		}

		model.addAttribute("restorent",restorent);
		model.addAttribute("buy", buyer);
		model.addAttribute("list",list);
		
		return "ViewOrder";
	}

/*------------------------- order placed confirmation---------------------------*/
  @GetMapping(value="/OrderConfirmation")//here id is our ordeid
  public String OrderConfirmation(@RequestParam("id") String id) {
	  
	  Buy buyer = buyRepository.findByOrderid(id);
      
	  Orders order = new Orders();
	  order.setUserId(buyer.getUserId());
	  order.setOrderid(id);
	  order.setMessage("Your order has been received by online-chatori-chat");
	  
	  buyer.setStatus("Recieved");
	  buyRepository.save(buyer);
	  
	  if(buyer.getStatus() == "Recieved" || buyer.getStatus() == "placed") {
	       orderRepository.save(order);
	  }
	  
	  return"redirect:/ViewOrder";
  }
  
/*------------------------------ getting detail of my plan ------------------------*/
  @GetMapping("/myplan")
  public String MyPlan(Model model,HttpServletRequest request) {
	  
	 HttpSession session = request.getSession(false);
     long clientid = (long)session.getAttribute("clientId");
	 
	 Payment payment = clientService.getMyPlanDetail(clientid);
     Restorent restorent = clientService.GetRestorentId(clientid);
     
	 model.addAttribute("payment",payment);
	 model.addAttribute("restorent",restorent);
	 
	 return"MyPlan";
  }
  
  /*--------------------------- goin to renew page -------------------------*/
  @GetMapping("/renewmyplan")
  public String renewmyplan(Model model, HttpServletRequest request) {
	  
    HttpSession session = request.getSession(false); 
    long clientid = (long)session.getAttribute("clientId");	
	  	
  	Restorent restorent = clientService.GetRestorentId(clientid);
  	Payment membership = clientService.getMyPlanDetail(clientid);
  
  	model.addAttribute("membership", membership);
  	model.addAttribute("restorent", restorent);
  	
  	return "ClientNotification";
	  // model.addAttribute("expire", "You membership is expire please renew your plan to use services!!!!");
  }
  
  @GetMapping("/renewplan") 
  public String renewplan(Model model,@RequestParam("membershipid") long membershipid, HttpServletRequest request) {

	 HttpSession session = request.getSession(true);
	 long clientid = (long)session.getAttribute("clientId");
	 
	 Optional<Payment> payment =  paymentRepository.findById(membershipid);	 
	 Restorent restorent = restorentRepository.findByClientId(clientid);
	 
	 
	 model.addAttribute("payment",payment);
	 model.addAttribute("restorent",restorent);
	 model.addAttribute("planexpire","Your Current Plan is Expire Today, Renew Your Plan To Use Our Services");
	 
	 return "pricecard";
  }
  
  /*-----------------------------------Client Notifications--------------------------------*/
  @GetMapping("/client-notification")
  public String getAllNotification(Model model,HttpServletRequest request) {
  	
  	HttpSession session = request.getSession(false); 
  	long clientid = (long)session.getAttribute("clientId");	
  	
  	Restorent restorent = clientService.GetRestorentId(clientid);
  	Payment membership = clientService.getMyPlanDetail(clientid);
  
  	model.addAttribute("membership", membership);
  	model.addAttribute("restorent", restorent);
  	
  	return "ClientNotification";
    }

/*---------------------------------- renew my plan after expiring---------------------------*/
  @GetMapping(value="/renewexpireplan")
  public String renewExpirePlan(Model model,HttpServletRequest request) {
	  
	  HttpSession session = request.getSession(false);
	  long clientid = (long)session.getAttribute("clientId");
	  
	 Restorent restorent = restorentRepository.findByClientId(clientid);
	 model.addAttribute("restorent",restorent);
	 model.addAttribute("renewexpireplan","You have successfully update your plan!!!!!");
	 
	 return "CProfile";
  }
}
