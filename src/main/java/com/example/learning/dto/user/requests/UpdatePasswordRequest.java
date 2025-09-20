package com.example.learning.dto.user.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
	@NotBlank(message = "User name cannot be empty")
	private String userName;
	@NotBlank(message = "Old Password cannot be empty")
	private String oldPassword;
	@NotBlank(message = "New Password cannot be empty")
	private String newPassword;
}
