package com.example.learning.utility;

public class StringUtils {
	public static final String LINK = "http://localhost:8080/file/get/";

	public static boolean isValidString(String string) {
		if (string == null || string.trim().equals("")) {
			return false;
		}
		return true;
	}
}
