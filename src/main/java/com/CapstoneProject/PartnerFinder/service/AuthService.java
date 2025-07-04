package com.CapstoneProject.PartnerFinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.CapstoneProject.PartnerFinder.DTOs.LoginDTO;
import com.CapstoneProject.PartnerFinder.security.JwtTokenProvider;

@Service
public class AuthService {
	
	private AuthenticationManager authenticationManager;

	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
    public AuthService(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
	
    public String login(LoginDTO loginDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
	
}
