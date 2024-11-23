package com.api.digital.security.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.digital.security.exceptions.CustomAccessDeniedHandler;
import com.api.digital.security.exceptions.CustomAuthenticationEntryPoint;
import com.api.digital.security.filter.JWTAuthenticationFilter;
import com.api.digital.security.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailService customUserDetailService;
	private final AccessDeniedHandler accessDeniedHandler;
	private final AuthenticationEntryPoint authenticationEntryPoint;


	public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
			CustomUserDetailService customUserDetailService, 
			AccessDeniedHandler accessDeniedHandler, 
			AuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.customUserDetailService = customUserDetailService;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
		http
			.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers(HttpMethod.POST, "/auth/login").permitAll() //Permite Login sem autenticação
			.requestMatchers(HttpMethod.GET, "/devices/**").hasAnyRole("ADMIN", "COMMON_USER")
			.requestMatchers(HttpMethod.PUT, "/devices/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.DELETE, "/devices/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST, "/vulnerabilities").hasAnyRole("ADMIN", "COMMON_USER")
			.requestMatchers(HttpMethod.GET, "/vulnerabilities/**").hasAnyRole("ADMIN", "COMMON_USER")
			.requestMatchers(HttpMethod.DELETE, "/vulnerabilities/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

}
