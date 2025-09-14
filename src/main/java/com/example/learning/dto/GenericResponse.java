package com.example.learning.dto;

import lombok.Data;

@Data
public class GenericResponse {
	private Status status = Status.FAILURE;
	private String messgae;
}
