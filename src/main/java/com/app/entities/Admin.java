package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "admins")
public class Admin {
	@Id
	@Column(length = 20,nullable = false)
	@Pattern(regexp="(^[6-9]\\d{9}$)",message = "contact number either blank or invalid")
	private String contactNumber;
	
	@Column(length = 50)
	@NotBlank(message="name can't be blank")
	@Size(min = 3, max = 50, message = "min 3 and max 50 name length allowed!")
	private String name;
		
	@Column(nullable = false)
	@NotBlank(message = "password can't be blank")
	private String password; 

	public Admin() {
		super();
		System.out.println("in admin default cnstr");
	}

	public Admin(
			@Pattern(regexp = "(^[6-9]\\d{9}$)", message = "contact number either blank or invalid") String contactNumber,
			@NotBlank(message = "name can't be blank") @Size(min = 3, max = 50, message = "min 3 and max 50 name length allowed!") String name,
			String password, String role) {
		super();
		this.contactNumber = contactNumber;
		this.name = name;
		this.password = password;
	}



	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [contactNumber=" + contactNumber + ", name=" + name + "]";
	}
}
