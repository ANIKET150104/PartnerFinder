package com.CapstoneProject.PartnerFinder.DTOs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;
}
