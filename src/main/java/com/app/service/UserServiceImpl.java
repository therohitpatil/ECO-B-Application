package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_excep.MyCustomException;
import com.app.dao.UserRepository;
import com.app.entities.User;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
		
	public UserServiceImpl() {
		super();
		System.out.println("in "+getClass().getName());
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUser(String contactNumber, String password) {
		//returns user whose password and contact numbers match
		return userRepository.findByIdAndPassword(contactNumber, password);
	}
}
