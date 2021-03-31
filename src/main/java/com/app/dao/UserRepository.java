package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	@Query("select u from User u where u.contactNumber=:contactNumber and u.password=:password")
	User findByIdAndPassword(String contactNumber,String password);
	// =:xyz from @Query must match with @Param("xyz")
}
