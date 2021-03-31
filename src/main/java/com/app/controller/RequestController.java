package com.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entities.Request;
import com.app.helper.Message;
import com.app.service.RequestServiceImpl;

@Controller
@RequestMapping("/request/")
public class RequestController {

	@Autowired
	private RequestServiceImpl requestService;
	
	//setting dataTimeFormate pattern as our need and using below
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public RequestController() {
		super();
		System.out.println("in cnstr of "+getClass().getName());
	}
	
	// shows request dashboard, where all requests are!
	@GetMapping("list")
	public String requestPage(Model model) {
		System.out.println("in request/index");
		List<Request> list = requestService.getAllRequest();
		System.out.println("requests : "+list);
		model.addAttribute("request", list);
		return "request/index";
	}
	
//	@PostMapping("add")
//	public String addStudent(@Valid Student student, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return "add-student";
//		}
//
//		studentRepository.save(student);
//		return "redirect:list";
//	}
	
	@GetMapping("add")
	public String addRequest(Request request) {
		return "request/addRequest";
	}
	
//	rideStatus;// completed/not completed
	@PostMapping("add")
	public String addedRequest(@Valid Request request, BindingResult result, Model model) {
		if (result.hasErrors()) {//for failure msg sent with error class for frontend
			model.addAttribute("message", new Message("failed to request","alert-danger"));//(msg,html class for msg)
			return "/request/addRequest";//sent back to request page again
		}
		Request newRequest = request;//new request created with default cnstr
		newRequest.setRequestTime(LocalDateTime.now().format(format).toString());
		//current dateTime formatted as per our requirement and set as ride requested time
		newRequest.setRideStatus(false);//ride status set false i.e.not completed
		requestService.addRequest(newRequest);//request added to DB
		return "redirect:/request/list";
	}
	
}
