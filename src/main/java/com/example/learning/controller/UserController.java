package com.example.learning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.dto.user.requests.CreateUserRequest;
import com.example.learning.dto.user.requests.UpdateUserRequest;
import com.example.learning.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
		return userService.createUser(createUserRequest);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
		return userService.updateUser(updateUserRequest);
	}

	@GetMapping("/data")
	public ResponseEntity<?> getAllData(@RequestParam   @Min(value = 1, message = "User Id must be greater than 1") Long userId) {
		return userService.getUserData(userId);
	}

	@GetMapping("/get-list/{userId}")
	public ResponseEntity<?> getFolderMapping(@PathVariable   @Min(value = 1, message = "User Id must be greater than 1") Long userId) {
		return userService.getFolders(userId);
	}

}
