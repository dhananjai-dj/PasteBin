package com.example.learning.dto;


import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
	
	private String token;
	private LocalDateTime createdTimeStamp;

}
