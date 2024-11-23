package com.api.digital.security.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
			throws IOException{
		String errorMessage = "Falha na autenticacao. ";
		
		Throwable cause = authException.getCause();
		if(cause != null && cause.getClass().getSimpleName().equals("ExpiredJwtException")) {
			 errorMessage += "Token Expirado. Por favor, faça login novamente.";
		}else {
			errorMessage += "Token invalido ou ausente.";
		}
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
	 
	}
}
