package com.CapstoneProject.PartnerFinder.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.CapstoneProject.PartnerFinder.DTOs.ApplicationDTO;
import com.CapstoneProject.PartnerFinder.DTOs.QualificationDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileResponse;
import com.CapstoneProject.PartnerFinder.DTOs.UserSkillDTO;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.service.ApplicationService;
import com.CapstoneProject.PartnerFinder.service.QualificationService;
import com.CapstoneProject.PartnerFinder.service.UserProfileService;
import com.CapstoneProject.PartnerFinder.service.UserService;
import com.CapstoneProject.PartnerFinder.service.UserSkillService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "REST APIs for Collaborator Resource")
public class UserController {

	private UserService userService;
	private QualificationService qualificationService;
	private UserProfileService userProfileService;
	private UserSkillService userSkillService;
	private ApplicationService applicationService;
	
	public UserController(UserService userService, 
			QualificationService qualificationService, 
			UserProfileService userProfileService, 
			UserSkillService userSkillService, 
			ApplicationService applicationService
			) {
		this.userService = userService;
		this.qualificationService = qualificationService;
		this.userProfileService = userProfileService;
		this.userSkillService = userSkillService;
		this.applicationService = applicationService;
	}
	
	@PostMapping
    public User createUser(@RequestBody User user) {

        return userService.createUser(user);

    }


    // User Profile Methods
    @PostMapping("/{userId}/qualifications")
    public ResponseEntity<QualificationDTO> addQualification(@RequestBody QualificationDTO qualification
            , @PathVariable("userId") Long userId
    ) {

        try {
			return ResponseEntity.ok(qualificationService.createQualification(qualification, userId));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add qualification", e);
		}
    }


    @PostMapping(value = "/{userId}/profileDetails", consumes = {"*/*"})
    public ResponseEntity<UserProfileResponse> addProfileDetails(@ModelAttribute UserProfileDTO userProfileDto, @PathVariable("userId") Long userId
    ) throws IOException {

        try {
			return new ResponseEntity<>(userProfileService.createUserProfile(userProfileDto, userId), HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add profile details", e);
		}

    }

    @PostMapping(value = "/{userId}/skills", consumes = {"*/*"})
    public ResponseEntity<UserSkillDTO>
    addSkillDetails(@RequestBody UserSkillDTO userSkillDto,
                      @PathVariable("userId") Long userId
    ) throws IOException {

        try {
			return new ResponseEntity<>(userSkillService.createSkill(userSkillDto, userId)
			        , HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add new skills", e);
		}
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {

        List<UserProfileDTO> users = userService.getAllUsers();


        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        try {
			return ResponseEntity.ok(userService.getUserById(userId));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Not Found", e);
		}
    }


    // Job Method Related to Employee Methods

    @PostMapping("/{userId}/projects/{projectId}/apply")
    public ResponseEntity<String> applyForProject(@PathVariable("userId") Long userId,
                                              @PathVariable("projectId") Long projectId) {

        return new ResponseEntity<>(applicationService.applyToProject(userId, projectId), HttpStatus.CREATED);

    }

    @PostMapping("/{userId}/projects/{projectId}/yourApplications/{applicationId}/cancel")
    public ResponseEntity<String> cancelProjectApplication(@PathVariable("userId") Long userId,
                                                       @PathVariable("projectId") Long projectId,
                                                       @PathVariable("applicationId") Long applicationId

    ) {

        return ResponseEntity.ok(applicationService.cancelApplication(userId, projectId , applicationId));

    }


    @GetMapping("/{userId}/projects/yourApplications")
    public ResponseEntity<List<ApplicationDTO>> getAllProjectApplicationByUser(
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(applicationService.getAllApplicationsByUser(userId));
    }
}
