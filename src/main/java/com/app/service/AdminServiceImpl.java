package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.AdminRepository;
import com.app.entities.Admin;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	
	public AdminServiceImpl() {
		super();
		System.out.println("in "+getClass().getName());
	}


	@Override
	public Admin getAdmin(String contactNumber, String password) {
		return adminRepository.findByIdAndPassword(contactNumber,password);
	}
//	getAdminByContactNumberAndPassword(contactNumber, Password).orElseThrow(()->new MyCustomException("Invalid admin credentials"));
}
