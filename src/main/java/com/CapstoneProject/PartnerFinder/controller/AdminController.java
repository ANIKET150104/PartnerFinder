package com.CapstoneProject.PartnerFinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CapstoneProject.PartnerFinder.DTOs.PosterDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserDTO;
import com.CapstoneProject.PartnerFinder.service.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "REST APIs for Admin Resource")
public class AdminController {
	
    @Autowired
    private AdminService adminService;

    @PostMapping("/ban/{id}")
    public ResponseEntity<String> banUser(@PathVariable Long id){
    	return adminService.banUserPoster(id);
    }
    @PostMapping("/unban/{id}")
    public ResponseEntity<String> unbanUser(@PathVariable Long id){
    	return adminService.unbanUserPoster(id);
    }
    @GetMapping("/poster-all")
    public ResponseEntity<List<PosterDTO>> getAllPosters() {

        List<PosterDTO> posters = adminService.getAllPosters();


        return new ResponseEntity<>(posters, HttpStatus.OK);
    }

    
    @GetMapping("/user-all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> users = adminService.getAllUsers();


        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/user-delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }
    
}