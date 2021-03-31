package com.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entities.Request;
import com.app.entities.User;
import com.app.helper.Message;
import com.app.service.RequestServiceImpl;
import com.app.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private RequestServiceImpl requestService;
	// setting dataTimeFormate pattern as our need and using below
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	public UserController() {
		super();
		System.out.println("in "+getClass().getName());
	}
	
	// /user/login handler
	@RequestMapping("/signin")
	public String userLogin(Model model) {
		System.out.println("in "+getClass().getName()+" /user/login method");
		model.addAttribute("user", new User());
		return "/user/login";
	}
	
	@PostMapping("signin")
	public String processAdminLogin(@Valid User user, BindingResult result,
			Model model, HttpSession session) {
		System.out.println("in process login of "+getClass().getName());
		try {
			String contactNumber = user.getContactNumber();
			String password = user.getPassword();
			User  user1= userService.getUser(contactNumber, password);
			if(user1==null) {
				System.out.println("user1 is null");
				model.addAttribute("message", new Message("Something went wrong! ", "alert-danger"));
				return "redirect:/user/signin";
			}
			System.out.println("user1 from user/signin"+user1);
			model.addAttribute("user",user1);
			session.setAttribute("user", user1);
			
			//adds msg to same page
			model.addAttribute("message", new Message("Successfully signin","alert-success"));
			
//			User user2 = (User)model.getAttribute("user");
//			System.out.println(user2);
			
			//fetch user specific requests
			List<Request> request1 = requestService.getRequestByUserId(user1.getContactNumber())
					.stream().filter(e->e.isRequestStatus()==true).collect(Collectors.toList());
			System.out.println(request1);
			model.addAttribute("requests", request1);
			session.setAttribute("requests", request1);
			return "user/dashboard";//on success sends user to dashboard
		}catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			model.addAttribute("message", new Message("Something went wrong! "+e.getMessage(), "alert-danger"));//adds message to login page with error encountered
			return "redirect:/user/login";	
		}
		
	}
	
	// /user/index handler to direct user to dashboard
	@GetMapping("dashboard")
	public String adminDashboard(Model map,HttpSession session) {
		User user = (User)map.getAttribute("user");
		System.out.println("user from user/dashboard"+user);
		return "user/dashboard";
	}
	
	@GetMapping("logout")
	public String adminLogout(HttpSession session,Model model,HttpServletRequest request,HttpServletResponse resp) {
		System.out.println("in logout "+model);
		//get user details from session scope n add it under current request scope
		model.addAttribute("details",model.getAttribute("user"));
		//invalidate session
		session.invalidate();
		//How to auto navigate the clnt to the next page (eg : home page) after a delay ?
		//Method of HttpServletRespose : public void setHeader(String name,String value)
		resp.setHeader("refresh", "5;url=/");
		return "user/logout";//actual view name /templates/admin/logout.html
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model, HttpSession session) {
		Request request = requestService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid request Id:" + id));
		model.addAttribute("request", request);
		session.setAttribute("request", request);
		System.out.println("user from user/edit"+(User)model.getAttribute("user"));
		return "user/editRequest";
	}

	@PostMapping("update/{id}")
	public String updateRequest(@PathVariable("id") int id, @Valid Request request, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			request.setRequestId(id);
			return "user/editRequest";
		}
		User user = (User)model.getAttribute("user");
		System.out.println("user from model user/update"+user);
		request = requestService.getRequestById(id);
		Request req = (Request)model.getAttribute("request");
		request.setBicycleAlotted(req.getBicycleAlotted());
		request.setRideStatus(true);// sets ride status as completed
		request.setFinishTime(LocalDateTime.now().format(format).toString());// finish time updated
		requestService.addRequest(request);
		model.addAttribute("requests", requestService.getAllRequest());
		session.setAttribute("requests", requestService.getAllRequest());
		System.out.println("user from user/update"+(User)model.getAttribute("user"));
		return "user/dashboard";
	}
	
	//add new request
	@GetMapping("add")
	public String addRequest(Request request) {
		return "user/addRequest";
	}
	
//	rideStatus;// completed/not completed
	@PostMapping("add")
	public String addedRequest(@Valid Request request, BindingResult result, Model model) {
		if (result.hasErrors()) {//for failure msg sent with error class for frontend
			model.addAttribute("message", new Message("failed to request","alert-danger"));//(msg,html class for msg)
			return "/user/addRequest";//sent back to request page again
		}
		Request newRequest = request;//new request created with default cnstr
		newRequest.setRequestTime(LocalDateTime.now().format(format).toString());
		//current dateTime formatted as per our requirement and set as ride requested time
		newRequest.setRideStatus(false);//ride status set false i.e.not completed
		requestService.addRequest(newRequest);//request added to DB
		return "redirect:/user/dashboard";
	}
	
}