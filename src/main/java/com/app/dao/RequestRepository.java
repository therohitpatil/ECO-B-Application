package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	
	List<Request> findAll();
	
}
