package com.CapstoneProject.PartnerFinder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CapstoneProject.PartnerFinder.DTOs.ApplicationDTO;
import com.CapstoneProject.PartnerFinder.DTOs.PosterApplicationDTO;
import com.CapstoneProject.PartnerFinder.service.ApplicationService;

import io.swagger.v3.oas.annotations.tags.Tag;

//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projects/applications")
@Tag(name = "REST APIs for Project Application")
public class ProjectApplicationController {


    private ApplicationService applicationService;

    public ProjectApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<PosterApplicationDTO>> getAllJobApplications() {

        return ResponseEntity.ok(applicationService.getAllApplications());
    }
    
    @GetMapping(value = "/{projectId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationByProjectId(
            @PathVariable("projectId") Long projectId
    ) {
        return ResponseEntity.ok(applicationService.getAllApplicationsByProject(projectId));
    }

}
