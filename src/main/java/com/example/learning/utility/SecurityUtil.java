package com.example.learning.utility;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.learning.dto.CustomPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityUtil {

	// Should be removed later to have dynamic key
	private static final String SECRET = "my-very-strong-secret-key-which-should-be-long";
	private static final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

	private final static Long expirationTime = 3600000L;
	// private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public static String generatePasswordHash(@NonNull String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public static boolean isSamePassword(@NonNull String password, @NonNull String encodeValue) {
		return bCryptPasswordEncoder.matches(password, encodeValue);
	}

	public static String generateJwtToken(String userName, Long id) {
		return Jwts.builder().setSubject(userName).claim("userId", id).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(key).compact();
	}

	public static CustomPrincipal getCustomPrincipal(String token) {
		CustomPrincipal customPrincipal = new CustomPrincipal();
		customPrincipal.setIsValid(false);
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			if (claims.getExpiration() != null && claims.getExpiration().after(new Date())) {
				customPrincipal.setUserName(claims.getSubject());
				customPrincipal.setUserId(claims.get("userId", Long.class));
				customPrincipal.setIsValid(true);
			}
		} catch (Exception e) {
		}
		return customPrincipal;
	}

}
