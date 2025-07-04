package com.CapstoneProject.PartnerFinder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CapstoneProject.PartnerFinder.DTOs.JWTResponseDto;
import com.CapstoneProject.PartnerFinder.DTOs.LoginDTO;
import com.CapstoneProject.PartnerFinder.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "REST APIs for Authentication Resource")
public class AuthController {
	
   
	private AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
    	JWTResponseDto jwtResponseDto = new JWTResponseDto();


        String token = authService.login(loginDto);
        jwtResponseDto.setAccessToken(token);


        return ResponseEntity.ok(jwtResponseDto);
    }
}
