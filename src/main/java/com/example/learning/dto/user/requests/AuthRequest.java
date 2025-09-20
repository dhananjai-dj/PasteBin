package com.example.learning.dto.user.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
	private String email;
	private String userName;
	@NotBlank(message = "User password cannot be blank")
	private String password;
}
