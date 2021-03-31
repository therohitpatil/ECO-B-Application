package com.app.service;

import com.app.entities.User;

public interface IUserService {
	
	User getUser(String contactNumber,String Password);
}
