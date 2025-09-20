package com.example.learning.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationFilter authenticationFilter;

	public SecurityConfig(AuthenticationFilter authenticationFilter) {
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disable CSRF for API calls
				.authorizeHttpRequests(auth -> auth.requestMatchers("/no-auth/**", "/file/**").permitAll() // public end points
						.anyRequest().authenticated() // everything else requires JWT
				).addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // register JWT filter
																										

		return http.build();
	}
}
