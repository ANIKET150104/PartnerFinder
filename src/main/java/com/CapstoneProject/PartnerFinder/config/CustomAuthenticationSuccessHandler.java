package com.CapstoneProject.PartnerFinder.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication
	) throws IOException, ServletException {
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String redirectURL = "/dashboard/user";
		if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			redirectURL = "/dashboard/admin";
		}
		else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_POSTER"))){
			redirectURL = "/dashboard/poster";
		}
		else {
			redirectURL = "/dashboard/user";
		}
		response.sendRedirect(redirectURL);
	}

}
