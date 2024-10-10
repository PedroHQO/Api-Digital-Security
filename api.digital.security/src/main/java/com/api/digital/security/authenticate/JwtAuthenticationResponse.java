package com.api.digital.security.authenticate;

public class JwtAuthenticationResponse {
	private String acessToken;
	private String tokenType = "Bearer";
	public JwtAuthenticationResponse(String acessToken) {

		this.acessToken = acessToken;
	}
	public String getAcessToken() {
		return acessToken;
	}
	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	

}
