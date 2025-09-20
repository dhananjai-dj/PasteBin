package com.example.learning.dto;

import lombok.Data;

@Data
public class CustomPrincipal {
	private String userName;
	private Long userId;
	private Boolean isValid;

}
