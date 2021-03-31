package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_excep.MyCustomException;
import com.app.dao.RequestRepository;
import com.app.entities.Request;
@Service
@Transactional
public class RequestServiceImpl implements IRequestService {

	@Autowired
	private RequestRepository requestRepository;
		
	public RequestServiceImpl() {
		super();
		System.out.println("in cnstr of "+getClass().getName());
	}

	//get perticular request by request id
	@Override
	public Request getRequestById(int id) {
		Request request = requestRepository.findById(id).orElseThrow(()->new MyCustomException("no request found"));
		return request;
	}
	
	//get all requests
	public List<Request> getAllRequest(){
		List<Request> requests = requestRepository.findAll();
		System.out.println("requests(service layer) : "+requests);
		return requests;
	}
//	Request request
	//method to get request by userid
	public List<Request> getRequestByUserId(String contactNumber) {
		List<Request> request= requestRepository.findAll()//finds all requests
				.stream()//streams them one by one
				.filter(e->e.getUserId().equals(contactNumber)).collect(Collectors.toList());//filters on contact number finds all request
		return request;
	}
	
	public void addRequest(Request request) {
		requestRepository.save(request);
	}

	public Optional<Request> findById(int id) {
		return requestRepository.findById(id);
	}

}
