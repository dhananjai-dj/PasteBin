package com.example.learning.utility;


import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.learning.dto.GenericResponse;
import com.example.learning.dto.Status;



public class GenericUtil {
	
	private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public static GenericResponse generateGenericResponse(boolean status, @NonNull String messgae) {
		GenericResponse genericResponse = new GenericResponse();
		if(status) {
			genericResponse.setStatus(Status.SUCCESS);
		}
		genericResponse.setMessgae(messgae);
		return genericResponse;
	}
	
	public static String generatePasswordHash(@NonNull String password) {
		return bCryptPasswordEncoder.encode(password);
	}
	
	public static boolean isSamePassword(@NonNull String password, @NonNull String encodeValue) {
		return bCryptPasswordEncoder.matches(password, encodeValue);
	}

}
