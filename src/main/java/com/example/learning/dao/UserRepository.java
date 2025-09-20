package com.example.learning.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.learning.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u.userName FROM User u")
	List<String> getAllUserNames();

	User getByUserName(String userName);

	User getByEmail(String email);
}
