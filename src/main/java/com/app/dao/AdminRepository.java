package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	
	@Query("select a from Admin a where a.contactNumber=:contactNumber and a.password=:password")
	Admin findByIdAndPassword(String contactNumber,String password);
	// =:xyz from @Query must match with @Param("xyz")
}
