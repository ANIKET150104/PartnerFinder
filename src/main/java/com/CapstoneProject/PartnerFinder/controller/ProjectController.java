package com.CapstoneProject.PartnerFinder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO;
import com.CapstoneProject.PartnerFinder.service.ProjectService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/projects")
@Tag(name = "REST APIs for Project Resource")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
    	this.projectService = projectService;
    }
    
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectByProjectId(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectService.getProjectByProjectId(projectId));
    }
    
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectResponseDTO>> searchProjects(@RequestParam(value = "projectCategory", required = false, defaultValue = "0000000") String projectCategory, 
    		                                                       @RequestParam(value = "isOpen", required = false, defaultValue = "0000000") boolean isOpen, 
    		                                                       @RequestParam(value = "techStack", required = false, defaultValue = "0000000") String techStack
    		                                                       ) {
        return ResponseEntity.ok(projectService.searchProject(projectCategory, isOpen, techStack));
    }
    @GetMapping("/my-projects")
    public ResponseEntity<List<ProjectResponseDTO>> getMyJoinedProjects(@RequestParam String email) {
        return ResponseEntity.ok(projectService.getProjectsByMemberEmail(email));
    }

}