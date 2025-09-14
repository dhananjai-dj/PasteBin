package com.example.learning.dto.user.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
	@NotBlank(message = "User name cannot be empty!")
	private String userName;
	@NotBlank(message = "Email name cannot be empty!")
	private String email;
}
