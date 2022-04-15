	package com.springboot.assessment3.controller;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.springboot.assessment3.model.Dish;
import com.springboot.assessment3.model.Orders;
import com.springboot.assessment3.model.Restorent;
import com.springboot.assessment3.model.User;
import com.springboot.assessment3.repository.BuyRepository;
import com.springboot.assessment3.repository.CardRepository;
import com.springboot.assessment3.repository.DishRepository;
import com.springboot.assessment3.repository.OrderRepository;
import com.springboot.assessment3.repository.UserRepository;
import com.springboot.assessment3.service.ClientService;
import com.springboot.assessment3.service.EmailService;
import com.springboot.assessment3.service.UserService;

import cn.apiclub.captcha.Captcha;

@Controller
public class UserController {

	@Autowired  private UserService userService;
	@Autowired 	private EmailService emailService;
	@Autowired 	private UserRepository userRepository;
	@Autowired  private DishRepository dishRepository;
    @Autowired  private CardRepository cardRepository;
	@Autowired  private  ClientService clientService;
	@Autowired  private  BuyRepository buyRepository;
	@Autowired  private  OrderRepository orderRepository;
/*----------------------- calling user-sign-up page for registration ----------------------------------*/
 
@GetMapping("/UserSignUp")
public String add(Model model) {
	User user = new User();
	getCaptcha(user);
	model.addAttribute("user", user);

	return "UserSignUp";
}	  
/*-----------Saving data of user-sign-up page into db and sending email with OTP-----------------*/
 
@PostMapping("/SaveUser")
public String saveUser(@ModelAttribute("user") User user, 
		               @RequestParam("email") String email, 
		               HttpSession session, Model model) {
	System.out.println("email:- "+email);
	
	Random random = new Random(1000);	
	long otp = random.nextInt(999999);		
	System.out.println("Otp:- "+otp);
	user.setOtp(otp);
	
	String to =email;
	String subject ="OTP for account varififcation";
	String message ="OTP for account varififcation with right credentials enter this "+otp+" ";
	
	
	
	if(user.getCaptcha().equals(user.getHiddenCaptcha())) {
		
		boolean success = emailService.sendEmail(subject, message, to);
		System.out.println(success);
		
		if(success) {
		System.out.println("email sent successfully");
		System.out.println("Captcha matched");
		userService.addUser(user);
		
		model.addAttribute("user", user);
		model.addAttribute("userloginmessage","You have done your Registeragion  successfully ");
		}
		return "UserLogin";
	}else {
		System.out.println("Enable to send email failed");
		model.addAttribute("errormessage","Invalid captcha Or May Be Something Went Wrogn Please Check And try again!!");
		
		getCaptcha(user);
		model.addAttribute("user", user);

		return "UserSignUp";
	}
}

/*----------------------- getting getCaptcha on image ----------------------------------*/
private void getCaptcha(User user) {
	Captcha captcha = CaptchaUtil.createCaptcha(250, 80);
	user.setHiddenCaptcha(captcha.getAnswer());
	user.setCaptcha("");
	user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
		
}
	
/*----------------------- notification controller for getting notification's ----------------------------------*/
@GetMapping("/notification")
public String notify(Model model,User user,HttpServletRequest request) {
	model.addAttribute("user", user);
	
    HttpSession session = request.getSession(false);	
    long userId = (long)session.getAttribute("id");
    
    List<Orders> order = userService.getAllNotification(userId);
    
    model.addAttribute("order",order);
	return "UserNotifications";
}
	
/*----------------------- calling user-login page for login  --------------------------------------*/

@GetMapping("/UserLogin")
public String home1(Model model){
	model.addAttribute("user", new User());
	return "UserLogin";
}

/*----------------------- ccalling user-login page for login validation ----------------------------------*/

@PostMapping("/UserLogin")
public String UserLogin(@ModelAttribute("user") User user,Model model, HttpServletRequest request) { 
    System.out.println("Varification statered");
   
    if(userService.userLogin(user.getEmail(), user.getPassword(), user.getOtp()) != null) {
    	
    	User authuser = userService.userLogin(user.getEmail(), user.getPassword(), user.getOtp());
    	
    	long userId = authuser.getId();
    	
        System.out.println("Session Created");       
        HttpSession session = request.getSession(true);//if getSession is true then it will gives us current  session object else it will creat new once
        request.getSession().setAttribute("id", userId);
        
        System.out.println("currently login uder Id is:- "+session.getAttribute("id"));
        
    	model.addAttribute("user", authuser);
    	model.addAttribute("successmessage","you have successfully login");
    	return "UserHome";
    }else {
    	model.addAttribute("loginerrormessage","Something went wrogn please check your credentials and try again");
    	return  "UserLogin";
    }
}

/*----------------------- ccalling user-login page for login validation ----------------------------------*/
@RequestMapping(value = {"/logout"})
public String logoutDo(HttpServletRequest request)  {

	HttpSession session = request.getSession(false);
	System.out.println("User "+session.getAttribute("id")+" is logOut");
	
	if(session != null) {
		session.invalidate();
		System.out.println("Session destroy");
	}
    return "redirect:/home";
}
/*----------------------- ccalling user-login page for login validation ----------------------------------*/
/*--- userhome which contain all the information of restorent it also show all the dishes for tht restorent */
@GetMapping("/UserHome")
public String home(Model model, User user, HttpServletRequest request) {
	
	model.addAttribute("user", user);
	return "UserHome";
}
	
/*------------searching detail's of restorent while entering character -------------*/

@RequestMapping("/search")
public String showRestorents(Model model,HttpServletRequest request, Restorent restorent, String keyword) {
	if(keyword!=null) {
		List<Restorent> list  = userService.getByKeyword(keyword);
	    model.addAttribute("list",list);
	} else {
		List<Restorent> list = userService.getAllRestorents();
	    model.addAttribute("list", list);
	}
	
	 
	HttpSession session  = request.getSession(false);
	System.out.println("currently loggedIn User is:- "+session.getAttribute("id"));
	
	return "UserHome";
}
	
/*--------- show available dishes for perticular restorent by using restorentId ---------------------*/	
	//@PathVariable annotation is used to read or extract value from uri
	
@GetMapping(value="/showDishesToUser")
public String ShowDishesToUser(Model model, 
		                       @RequestParam("restorentId") long RestorentId) {		
	
	List<Dish> dishes = userService.getAllDishesFromRestorent(RestorentId);
	model.addAttribute("dishes", dishes);
    model.addAttribute("RestorentId",RestorentId);
    System.out.println(RestorentId);
    return "showDishesToUser";
}	
	
/*---------------------	add perticular dish and by then -------------------------------------- */
@GetMapping(value="/addtocard")
public String addTocard(Model model , HttpServletRequest request ,@RequestParam("RestorentId") long RestorentId, @RequestParam("id") long dishid, @ModelAttribute Card card) {
	
	System.out.println("user added dish from this restorent:- "+RestorentId);
	HttpSession session = request.getSession(false);
	long userId = (long) session.getAttribute("id");
	System.out.println(userId);
	
    Dish dish = this.userService.getById(dishid);
	
    ModelAndView mav = new ModelAndView("showDishesToUser");
	card.setDishid(dishid);
	card.setDishName(dish.getDishName());
	card.setDishimage(dish.getDishImage());
	card.setDishPrice(dish.getDishPrice());
	card.setRestorentId(dish.getRestorent().getRestorentId());
    card.setUserId(userId);
    
	this.cardRepository.save(card);
	
	model.addAttribute("dish",dish);
	model.addAttribute("card",card);
	model.addAttribute("message3","dish successfully added to the card ");
	
	long restroid = card.getRestorentId();
	System.out.println(card.getRestorentId());
	
	return "addtocard";
	//return "redirect:/showDishesToUser?restorentId=" +restroid;
}

/*--------------------------------	open change change password home page  --------------------------------------*/
	
@GetMapping(value="/change-password-home")
public String changePasswordHome(Model model) {		
	model.addAttribute("user", new User());
	System.out.println("change password home page");
	return "changepassword";
}

/*-------------------------------- changing posword by providing new one --------------------------------------*/

//@RequestParam is used to read data from HTML form data provided by the user 
@PostMapping(value="/change-password-execution")
public String changePassword(@ModelAttribute("user") User user,
		                     @RequestParam("password") String password,
		                     @RequestParam("newPassword") String newPassword,
		                     Model model) {
	
	String oldPassword = user.getPassword();
	System.out.println("oldPassword:- "+oldPassword);
	
	long id1 = user.getId();
	System.out.println("id:- "+id1);
	
	System.out.println("password:-"+password);
	System.out.println("newPassword:- "+newPassword);
	
	//this condition will checked weather we have entered the right credentials or not
	if(userRepository.findByPassword(password) != null) {
		User currentUser = this.userRepository.findByPassword(password);
		long id = currentUser.getId();
		System.out.println("id:- "+id);
		System.out.println(currentUser);
		
		currentUser.setPassword(newPassword);
		userRepository.save(currentUser);
		System.out.println("passwored changed"+currentUser.getId());
		model.addAttribute("currentUser", currentUser);	
		model.addAttribute("changepassword","You have successfully change your password");
		return"changepassword";		
	}else {		
		System.out.println("passwored aren't currectly spealled!!!");
		model.addAttribute("ChangePasswordErrorMessage","Your Old password Are Not Match");
		return"changepassword";
	}
  }
/*---------------------------- Deleting User ------------------------------------------------------*/
@GetMapping("/delete-account")
public String deleteAccount(HttpServletRequest request,Model model) {
	HttpSession session = request.getSession(false);
	long userid = (long)session.getAttribute("id");
	
	userService.deleteUserAccount(userid);
	model.addAttribute("delete","You Successfully Delete Your Account!!!!!!!!!");
	return"home"; 
}

/*------------------------------------- all added dishes--------------------------------------------*/
@GetMapping(value="/AllAddedDishes")
public ModelAndView getAllAddedDishes(Model model,HttpServletRequest request) {
 
	 HttpSession session = request.getSession(false);
	 long userId = (long)session.getAttribute("id");
	
	 ModelAndView mav = new ModelAndView("list-alladdeddishes");
	 mav.addObject("card",userService.getAllDishes(userId));	
	
	 List<Card> cards = (List<Card>) userService.getAllDishes(userId);
	
	 System.out.println(cards.toString());
	 
	int total=0 ;
	 for(Card c : cards){
		 total= (int) (total + c.getDishPrice());
	 }
	 model.addAttribute("total",total);
	 System.out.println(total);
	 
	 return mav;	
  }

/*------------------------------------- Update all added dishes--------------------------------------------*/
@GetMapping(value="/update-added-dishes/{dishid}")
public String updateaddeddishes(@PathVariable long dishid,
		                        @RequestParam("cardvalue") String cardvalue,
		                        @ModelAttribute("card") Card card){

	System.out.println("card has been called");
	System.out.println("current id:- "+dishid);
	Card currentCard = cardRepository.findByDishid(dishid);
	
	 System.out.println("current added dishes:- "+currentCard.toString()); 
	
	 int totalprice = Integer.parseInt(cardvalue); 
	 int netprice = (int) (totalprice *(currentCard.getDishPrice())); 
	 System.out.println("net price:- "+netprice);
	
	 currentCard.setDishPrice(netprice);
	 	
	 cardRepository.save(currentCard);
	return"redirect:/AllAddedDishes";
  }
/*---------------------------------------------My Order--------------------------------------*/
@GetMapping(value="/MyOrder")
public String MyOrder(Model model,HttpServletRequest request) {
	
   HttpSession session =  request.getSession(false);
   long userid = (long) session.getAttribute("id");
   System.out.println("MyOrder: " + userid);
  
   List<Buy> buy = buyRepository.findByUserId(userid);
 
   model.addAttribute("buy",buy);
   System.out.println(buy);
   
   return "MyOrder";
}

/*----------------------- cancel order----------------------------*/
@GetMapping(value="/cancel-order") 
public String CancelMyOrder(@RequestParam("orderid") String orderid) {
	
	System.out.println("cancel order:- "+orderid);
	
	Buy order = buyRepository.findByOrderid(orderid);
	
	order.setStatus("order-cancel");
	
	buyRepository.save(order);
	System.out.println("order cancel"+orderid);
	
	return "redirect:/MyOrder";
  }
}
