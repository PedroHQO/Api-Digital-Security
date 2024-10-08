package com.api.digital.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.digital.security.authenticate.JWTTokenProvider;

@SpringBootTest
public class JwtProviderTest {
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Test
	public void testGenerateToken() {
		String token = jwtTokenProvider.generateToken("userTest");
		assertNotNull(token);
	}
	
	@Test
	public void testValidateToken() {
		String token = jwtTokenProvider.generateToken("userTest");
		assertTrue(jwtTokenProvider.validateToken(token));
	}
	
	@Test
	public void testGetUsernaeFromJWT() {
		String token = jwtTokenProvider.generateToken("userTest");
		String username = jwtTokenProvider.getUsernameFromJWT(token);
		assertEquals("userTest", username);
	}

}
