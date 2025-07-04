package com.CapstoneProject.PartnerFinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CapstoneProject.PartnerFinder.model.ProjectCategory;
import com.CapstoneProject.PartnerFinder.service.ProjectCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/projectCategories")
@Tag(name = "REST APIs for Project Categories")
public class ProjectCategoryController {

	private ProjectCategoryService projectCategoryService;

    @Autowired
    public ProjectCategoryController(ProjectCategoryService projectCategoryService) {
        this.projectCategoryService = projectCategoryService;
    }
    @PostMapping
    public ResponseEntity<ProjectCategory> addProjectCategory(@RequestBody ProjectCategory projectCategory) {

        return new ResponseEntity<>(projectCategoryService.createProjectCategory(projectCategory), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ProjectCategory>> getAllProjectCategories() {

        return ResponseEntity.ok(projectCategoryService.getAllProjectCategories());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProjectCategory(@PathVariable("id") Long id,
                                                         @RequestBody ProjectCategory projectCategory) {

        try {
			return ResponseEntity.ok(projectCategoryService.updateProjectCategory(id, projectCategory));
		} catch (Exception e) {
			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found");
		}
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectCategory(@PathVariable("id") Long id) {

        try {
			return ResponseEntity.ok(projectCategoryService.deleteProjectCategoryById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found");
		}
    }

}
