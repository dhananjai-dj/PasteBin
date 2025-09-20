package com.example.learning.dto;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class GenericResponse {
	private Status status;
	private String messgae;

	public GenericResponse(boolean status, @NonNull String message) {
		this.status = status ? Status.SUCCESS : Status.FAILURE;
		this.messgae = message;
	}

}
