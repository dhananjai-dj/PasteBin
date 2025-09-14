package com.example.learning.dto.file.responses;

import lombok.Data;

@Data
public class PasswordRequiredResponse {
	private Long fileId;
	private String message;
}
