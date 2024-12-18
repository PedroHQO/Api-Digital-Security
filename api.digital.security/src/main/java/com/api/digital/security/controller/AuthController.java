package com.api.digital.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.digital.security.authenticate.JWTTokenProvider;
import com.api.digital.security.authenticate.JwtAuthenticationResponse;
import com.api.digital.security.model.LoginRequest;

//Para evitar complexidade e repetição, tornei esta classe disponivel tanto para Devices quanto para vunerabilities e demais
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	JWTTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		String token = jwtTokenProvider.generateToken(loginRequest.getUsername());
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	
	
}
