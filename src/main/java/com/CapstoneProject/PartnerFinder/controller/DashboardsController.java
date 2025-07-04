package com.CapstoneProject.PartnerFinder.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "REST APIs for Dashboard")
public class DashboardsController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('COLLABORATOR')")
    public String userDashboard() {
        return "Welcome to the USER dashboard";
    }

    @GetMapping("/poster")
    @PreAuthorize("hasRole('POSTER')")
    public String posterDashboard() {
        return "Welcome to the POSTER dashboard";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Welcome to the ADMIN dashboard";
    }
}
