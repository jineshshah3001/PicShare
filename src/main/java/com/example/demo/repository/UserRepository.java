package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User ,String>{
	 	
		boolean existsByUserid(String userId);
		User findByUserid(String userId);
		List<User> findAll();
}