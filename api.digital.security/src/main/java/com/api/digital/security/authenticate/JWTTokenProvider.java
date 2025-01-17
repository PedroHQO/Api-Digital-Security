package com.api.digital.security.authenticate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;



@Component
public class JWTTokenProvider {
	
	@Value("${jwt.expiration}")
	private int jwtExpirationInMs;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	//Metodo para gerar o token
	public String generateToken(String username) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		
		Key key = getSigningKey();
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(key ,SignatureAlgorithm.HS512)
				.compact();
	}

	//metodo para obter o username do toke
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	//Metodo para validar o token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	//metodo auxiliar para obter a chave assinante codificada em Base64
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	
}
