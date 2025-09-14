package com.example.learning.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.learning.dto.GenericResponse;
import com.example.learning.exception.FileException;
import com.example.learning.exception.FolderException;
import com.example.learning.exception.UserException;
import com.example.learning.utility.GenericUtil;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionController {
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<GenericResponse> handleFileException(FileException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(GenericUtil.generateGenericResponse(false, exception.getMessage()));
	}
	
	@ExceptionHandler(FolderException.class)
	public ResponseEntity<GenericResponse> handleFolderException(FolderException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(GenericUtil.generateGenericResponse(false, exception.getMessage()));
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<GenericResponse> handleUserException(UserException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(GenericUtil.generateGenericResponse(false, exception.getMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException exception) {
	    Map<String, String> errors = new HashMap<>();
	    exception.getConstraintViolations().forEach(violation -> {
	        String field = violation.getPropertyPath().toString();
	        errors.put(field, violation.getMessage());
	    });
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
