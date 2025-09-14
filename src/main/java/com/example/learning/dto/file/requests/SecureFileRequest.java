package com.example.learning.dto.file.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecureFileRequest {
	@NotNull(message = "File id is manditory")
	@Min(value = 1, message = "Id must be greater than 0")
	private Long id;
	@NotBlank(message = "Password is manditory")
	private String password;
}
