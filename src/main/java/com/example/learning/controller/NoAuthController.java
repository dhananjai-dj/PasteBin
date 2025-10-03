package com.example.learning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.dto.user.requests.AuthRequest;
import com.example.learning.dto.user.requests.CreateUserRequest;
import com.example.learning.infra.BloomFilterService;
import com.example.learning.service.SecurityService;
import com.example.learning.service.UserService;
import com.example.learning.utility.StringUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/open")
public class NoAuthController {

	private final BloomFilterService bloomFilterService;
	private final SecurityService securityService;
	private final UserService userService;

	public NoAuthController(BloomFilterService bloomFilterService, SecurityService securityService,
			UserService userService) {
		this.bloomFilterService = bloomFilterService;
		this.securityService = securityService;
		this.userService = userService;
	}

	@GetMapping("/username-availablility")
	public Boolean isUserNameAvailabe(@RequestParam String userName) {
		return StringUtils.isValidString(userName) && !bloomFilterService.isContains(userName);
	}

	@PostMapping("/jwt-token")
	public ResponseEntity<?> getJwtToken(@Valid @RequestBody AuthRequest authRequest) {
		return securityService.getJwtToken(authRequest);
	}

	@GetMapping("/oauth-token")
	public ResponseEntity<?> getJwtToken(@AuthenticationPrincipal OAuth2User oAuth2User) {
		return securityService.getJwtTokenForOauth(oAuth2User);
	}

	@PostMapping("/add-user")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
		return userService.createUser(createUserRequest);
	}
}
