package com.example.learning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.dto.CustomPrincipal;
import com.example.learning.dto.user.requests.UpdatePasswordRequest;
import com.example.learning.dto.user.requests.UpdateUserRequest;
import com.example.learning.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}


	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@AuthenticationPrincipal CustomPrincipal principal, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
		return userService.updateUser(principal.getUserId(), updateUserRequest);
	}

	@PutMapping("update-password")
	public ResponseEntity<?> updateUserPassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
		return userService.updateUserPassword(updatePasswordRequest);
	}

	@GetMapping("/data")
	public ResponseEntity<?> getAllData(@AuthenticationPrincipal CustomPrincipal principal) {
		return userService.getUserData(principal.getUserId());
	}

	@GetMapping("/get-list")
	public ResponseEntity<?> getFolderMapping(@AuthenticationPrincipal CustomPrincipal principal) {
		return userService.getFolders(principal.getUserId());
	}

}
