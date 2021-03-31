package com.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

import com.app.entities.Admin;
import com.app.entities.Request;
import com.app.helper.Message;
import com.app.service.AdminServiceImpl;
import com.app.service.RequestServiceImpl;

@Controller
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private AdminServiceImpl adminService;
	@Autowired
	private RequestServiceImpl requestService;

	// setting dataTimeFormate pattern as our need and using below
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public AdminController() {
		System.out.println("in ctor of" + getClass().getName());
	}

	// /admin/login handler
	@RequestMapping("signin")
	public String adminLogin(Model model) {
		System.out.println("in " + getClass().getName() + " /admin/login method");
		model.addAttribute("admin", new Admin());
		return "/admin/login";
	}

	// /admin/login handler to process login form
	// if success direct to dashboard, if fail direct to same page
	@PostMapping("signin")
	public String processAdminLogin(@Valid Admin admin, BindingResult result, Model model, HttpSession session) {
		System.out.println("in process login of " + getClass().getName());
		try {
			String contactNumber = admin.getContactNumber();
			String password = admin.getPassword();
			Admin admin1 = adminService.getAdmin(contactNumber, password);
			if (admin1 == null) {
				System.out.println("admin1 is null");
				model.addAttribute("message", new Message("Something went wrong! ", "alert-danger"));
				return "redirect:/admin/signin";
			}
			System.out.println("Admin1" + admin1);
//			model.addAttribute("admin", admin1);
//			model.addAttribute("admin", admin1);
			session.setAttribute("admin", admin1);
			// adds msg to same page
			model.addAttribute("message", new Message("Successfully signin", "alert-success"));
			return "admin/dashboard";// on success sends admin to dashboard
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			model.addAttribute("message", new Message("Something went wrong! " + e.getMessage(), "alert-danger"));// adds
																													// message
																													// to
																													// login
																													// page
																													// with
																													// error
																													// encountered
			return "redirect:/admin/signin";
		}

	}

	@GetMapping("dashboard")
	public String adminDashboard(Model map, HttpSession session) {
		return "admin/dashboard";
	}

	@GetMapping("logout")
	public String adminLogout(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse resp) {
		System.out.println("in logout " + model);
		// get user details from session scope n add it under current request scope
		model.addAttribute("details", model.getAttribute("admin"));
		// invalidate session
		session.invalidate();
		// How to auto navigate the clnt to the next page (eg : home page) after a delay
		// ?
		// Method of HttpServletRespose : public void setHeader(String name,String
		// value)
		resp.setHeader("refresh", "5;url=/");
		return "admin/logout";// actual view name /templates/admin/logout.html
	}

	// method for showing requests to admin
	@GetMapping("requests")
	public String getAdminRequests(Model model, HttpSession session) {
		System.out.println("in request/index");
		List<Request> list = requestService.getAllRequest();
		System.out.println("requests : " + list);
		model.addAttribute("requests", list);
		session.setAttribute("requests", list);
		return "admin/index";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model, HttpSession session) {
		Request request = requestService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid request Id:" + id));
		model.addAttribute("request", request);
		session.setAttribute("request", request);
		return "admin/editRequest";
	}

	@PostMapping("update/{id}")
	public String updateRequest(@PathVariable("id") int id, @Valid Request request, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			request.setRequestId(id);
			return "admin/editRequest";
		}
		request = requestService.getRequestById(id);
		Request req = (Request)model.getAttribute("request");
		request.setBicycleAlotted(req.getBicycleAlotted());
		request.setRequestStatus(true);// sets ride status ad booked
		request.setBookedTime(LocalDateTime.now().format(format).toString());// booking time updated
		requestService.addRequest(request);
		model.addAttribute("requests", requestService.getAllRequest());
		session.setAttribute("requests", requestService.getAllRequest());
		return "admin/index";
	}

}
