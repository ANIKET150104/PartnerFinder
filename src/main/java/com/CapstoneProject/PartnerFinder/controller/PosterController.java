package com.CapstoneProject.PartnerFinder.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.CapstoneProject.PartnerFinder.DTOs.PosterApplicationDTO;
import com.CapstoneProject.PartnerFinder.DTOs.PosterDTO;
import com.CapstoneProject.PartnerFinder.DTOs.ProjectDTO;
import com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileResponse;
import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.Project;
import com.CapstoneProject.PartnerFinder.service.ApplicationService;
import com.CapstoneProject.PartnerFinder.service.PosterProfileService;
import com.CapstoneProject.PartnerFinder.service.PosterService;
import com.CapstoneProject.PartnerFinder.service.ProjectService;

import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/posters")
@Tag(name = "REST APIs for Poster Resource")
public class PosterController {
	
	private PosterService posterService;
    private ProjectService projectService;
    private ApplicationService applicationService;
    private PosterProfileService posterProfileService;


    @Autowired
    public PosterController(PosterService posterService, 
    		ProjectService projectService, 
    		ApplicationService applicationService,
    		PosterProfileService posterProfileService
    ) {
        this.posterService = posterService;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.posterProfileService = posterProfileService;
    }

    // Posters Related Functions
    @PostMapping
    public ResponseEntity<Poster> createPoster(@RequestBody Poster poster) {

        return new ResponseEntity<>(posterService.createPoster(poster), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PosterDTO>> getAllPosters() {
        return ResponseEntity.ok(posterService.findAllPosters());
    }
    
    // Posters Profile Related Functions
    @PostMapping(value = "/{posterId}/profileDetails", consumes = {"*/*"})
    public ResponseEntity<UserProfileResponse> addProfileDetails(@ModelAttribute UserProfileDTO userProfileDto, @PathVariable("posterId") Long posterId
    ) throws IOException {

        try {
			return new ResponseEntity<>(posterProfileService.createPosterProfile(userProfileDto, posterId), HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add profile details", e);
		}

    }

    // Posters project Related Functions

    @PostMapping(value = "/{posterId}/projects", consumes = {"*/*"})
    public ResponseEntity<String> createProject(@ModelAttribute ProjectDTO projectDto,
                                            @PathVariable("posterId") Long posterId)
            throws IOException {

        return new ResponseEntity<>(projectService.createProject(projectDto, posterId), HttpStatus.CREATED);

    }
    
    @GetMapping("/members/{projectId}")
    public ResponseEntity<List<UserDTO>> getApprovedMembers(@PathVariable Long projectId){
    	Project project;
		try {
			project = projectService.getProjectById(projectId);
			List<UserDTO> members = project.getMembers()
	    			.stream()
	    			.map(user -> new UserDTO())
	    			.collect(Collectors.toList());
	    	return ResponseEntity.ok(members);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found", e);
		}
    }

    @PutMapping("/{posterId}/myApplications/{applicationId}")
    public ResponseEntity<String> updateProjectApplicationsStatus(
            @PathVariable("applicationId") Long applicationId,
            @RequestParam("status") String status) {

        try {
			return new ResponseEntity<>(applicationService.updateApplicationStatusByPoster(applicationId, status), HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
		}

    }
    
    @PostMapping("/approve-member/{applicationId}")
    public ResponseEntity<String> approveApplication(@PathVariable Long applicationId){
    	
    	Project project;
		try {
			project = posterService.approveApplication(applicationId);
			if(project != null) {
		    	   return ResponseEntity.ok("Application Approved, added to members");
		    }else {
		    	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
		    }
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application Not Found", e);
		}
    	
    }
    
    @PostMapping("/projects/{projectId}/applications/{applicationId}/reject")
    public ResponseEntity<String> rejectApplication(@PathVariable Long projectId, @PathVariable Long applicationId){
    	try {  
    	   return new ResponseEntity<>(applicationService.rejectApplication(projectId, applicationId), HttpStatus.OK);
    	}catch(Exception e){
    	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
        }
    }


    @GetMapping(value = "/{posterId}/projects")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjectsByPoster(@PathVariable("posterId") Long posterId) {
        return ResponseEntity.ok(projectService.findByAllProjectsByPosterId(posterId));
    }


    @GetMapping(value = "/{posterId}/myApplications")
    public ResponseEntity<List<PosterApplicationDTO>> getAllApplicationByPosterId(
            @PathVariable("posterId") Long posterId
    ) {
        return ResponseEntity.ok(applicationService.getAllApplicationsByPoster(posterId));
    }


    @DeleteMapping({"/{posterId}/projects/{projectId}"})
    public ResponseEntity<String> deleteProject(@PathVariable("posterId") Long posterId,
                                            @PathVariable("projectId") Long projectId) {

        return ResponseEntity.ok(projectService.deleteProject(posterId, projectId));

    }
    
}