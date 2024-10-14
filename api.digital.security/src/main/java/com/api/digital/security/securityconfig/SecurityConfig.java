package com.api.digital.security.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.digital.security.filter.JWTAuthenticationFilter;
import com.api.digital.security.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailService customUserDetailService;


	public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
			CustomUserDetailService customUserDetailService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.customUserDetailService = customUserDetailService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/auth/login").permitAll()
			.requestMatchers("/devices").authenticated()
			.anyRequest().permitAll()
			.and()
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

}
