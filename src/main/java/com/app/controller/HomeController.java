package com.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dao.UserRepository;
import com.app.entities.User;
import com.app.helper.Message;

@Controller
public class HomeController {	
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	//this is home handler
	@RequestMapping("/")
	public String home(Model model) {
		System.out.println("in home controller /test");
		model.addAttribute("title","Home - ECO-B Application");
		return "home";
	}
	
	//this is about handler
	@RequestMapping("/about")
	public String about(Model model) {
		System.out.println("in home controller /about");
		model.addAttribute("title","Home ECO-B Application");
		return "about";
	}
	
	//this is signup handler
	@RequestMapping("/signup")
	public String signup(Model model) {
		System.out.println("in home controller /signup");
		model.addAttribute("title","Register to ECO-B Application");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	//handler for registering user
//	@RequestMapping(value = "register", method = RequestMethod.POST)
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement,
			Model model, HttpSession session) {
		System.out.println(getClass().getName() + " 's /register method");
		try {
			if (!agreement) {
				System.out.println("you have not agreed t&c");
				throw new Exception("you have not agreed t&c");
			} //if agreement t&c not agreed shows msg on server and client also

			if(result.hasErrors()){
				System.out.println("Error "+result.toString());
				model.addAttribute("user", user);
				return "signup";
			}//if there's error in field-value-constraints error printed and returned to same page
			
			//encrypting password
			//user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			//shows user data and agreement from user
			System.out.println("agreement : " + agreement);//shows agreement status

			this.userRepository.save(user);//adds user to users table, returns User

			model.addAttribute("user", new User());//adds null user to form parameters

			session.setAttribute("message", new Message("Successfully Registered","alert-success"));
			return "signup";
		} catch (Exception e) {//if exception occours 
			e.printStackTrace();//prints stack trace on server console
			model.addAttribute("user", user);//adds partially filled user details to form on client side
			session.setAttribute("message", new Message("Something went wrong! "+e.getMessage(), "alert-danger"));//adds message to form page with error encountered
			return "signup";
		}
		
	}
	
	//handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		System.out.println("in "+getClass().getName()+" /signin method");
		model.addAttribute("title", "Login page");
		return "login";
	}
	
}
