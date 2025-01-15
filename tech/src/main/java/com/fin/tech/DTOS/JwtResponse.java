package com.fin.tech.DTOS;

public class JwtResponse {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

	public JwtResponse() {
		super();
	}

	public JwtResponse(String token) {
		super();
		this.token = token;
	}
	
	
}
