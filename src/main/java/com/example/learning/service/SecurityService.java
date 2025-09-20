package com.example.learning.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.learning.dto.AuthResponse;
import com.example.learning.dto.GenericResponse;
import com.example.learning.dto.user.requests.AuthRequest;
import com.example.learning.entity.User;
import com.example.learning.utility.SecurityUtil;
import com.example.learning.utility.StringUtils;

import org.slf4j.LoggerFactory;

@Service
public class SecurityService {

	private final Logger logger = LoggerFactory.getLogger(SecurityService.class);
	private final UserService userService;

	public SecurityService(UserService userService) {
		this.userService = userService;
	}

	public ResponseEntity<?> getJwtToken(AuthRequest authRequest) {
		String email = authRequest.getEmail();
		String userName = authRequest.getUserName();
		String password = authRequest.getPassword();
		User user = null;
		try {
			if (StringUtils.isValidString(userName)) {
				user = userService.getUserByUserNameAndPassword(userName, password);
			} else if (StringUtils.isValidString(email)) {
				user = userService.getUserByEmailAndPassword(email, password);
			}
			if (user != null) {
				String token = SecurityUtil.generateJwtToken(userName, user.getId());
				LocalDateTime createdTimeStamp = LocalDateTime.now();
				AuthResponse authResponse = AuthResponse.builder().createdTimeStamp(createdTimeStamp).token(token)
						.build();
				return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
			}
		} catch (Exception e) {
			logger.error("Error in getJwtToken method {}", e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body((new GenericResponse(false, "Wrong Credentials entred!!!")));
	}

}
