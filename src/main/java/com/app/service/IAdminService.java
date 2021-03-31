package com.app.service;

import com.app.entities.Admin;

public interface IAdminService {
	
	Admin getAdmin(String contactNumber,String Password);
}
