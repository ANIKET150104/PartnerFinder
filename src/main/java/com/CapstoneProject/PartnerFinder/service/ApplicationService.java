package com.CapstoneProject.PartnerFinder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

import com.CapstoneProject.PartnerFinder.DTOs.ApplicationDTO;
import com.CapstoneProject.PartnerFinder.DTOs.PosterApplicationDTO;
import com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO;
import com.CapstoneProject.PartnerFinder.model.Application;
import com.CapstoneProject.PartnerFinder.model.Project;
import com.CapstoneProject.PartnerFinder.model.RequestStatus;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.repo.ApplicationRepository;
import com.CapstoneProject.PartnerFinder.repo.ProjectRepository;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;

@Service
public class ApplicationService {
	
    
    private ApplicationRepository appRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ApplicationService(
    		ApplicationRepository appRepository,
    		UserRepository userRepository, 
    		ProjectRepository projectRepository, 
    		ModelMapper modelMapper) {
    	this.appRepository = appRepository;
    	this.userRepository = userRepository;
    	this.projectRepository = projectRepository;
    	this.modelMapper = modelMapper;
    }
    public String applyToProject(Long userId, Long projectId) {
    	User user;
		try {
			user = checkUserExistence(userId);
			Project project = checkProjectExistence(projectId);
	        
	        Application application = new Application();
	        
	        if (!project.isOpen() || (project.getRequiredMembers() <= 0)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project is closed for applications or team limit reached");
	        }
	        
	        application.setUser(user);
	        application.setProject(project);
	        application.setAppliedDate(LocalDateTime.now());
	        application.setStatus(RequestStatus.PENDING.toString());
	        appRepository.save(application);
	        return "Job Applied Successfully";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while applying", e);
		}
       
    }

    public List<ApplicationDTO> getAllApplicationsByUser(Long userId) {

        List<Application> projectApplications = appRepository.findAllApplicationByUserId(userId);

        List<ApplicationDTO> AllUserApplications = projectApplications.stream().map((projectApp) -> {
                    ApplicationDTO applicationDto = new ApplicationDTO();
                    // Fill the data
                    applicationDto.setApplicantionId(projectApp.getId());
                    applicationDto.setStatus(projectApp.getStatus());
                    applicationDto.setAppliedDate(projectApp.getAppliedDate());
                    applicationDto.setProjectResponse(mapToProjectDTO(projectApp.getProject()));
                    return applicationDto;
                }
        ).collect(Collectors.toList());
        
        return AllUserApplications;
    }
    
    public String cancelApplication(Long userId, Long projectId, Long applicationId) {

        try {
        	User user = checkUserExistence(userId);
			Project project = checkProjectExistence(projectId);
			if(user == null && project == null) {
				return "Invalid Id";
			}
			Application application = checkProjectApplicationExistence(applicationId);

	        appRepository.delete(application);

	        return "Project Application Cancel Successfully";
		
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while canceling", e);
		}
    }
    public List<PosterApplicationDTO> getAllApplicationsByPoster(Long posterId) {

        return appRepository.findAllApplicationForPoster(posterId).stream().collect(Collectors.toList());
    }

    public String updateApplicationStatusByPoster(Long applicationId, String status) throws Exception {

        Application application = checkProjectApplicationExistence(applicationId);

        if (application.getStatus().equals(RequestStatus.REJECTED.toString())) {
            throw new Exception("This Application is Already Canceled , You can't Change it");
        }

        application.setStatus(status);
        appRepository.save(application);

        return "Application Status Changed Successfully";

    }
    
    public  List<PosterApplicationDTO> getAllApplications() {
        return appRepository.findAllApplication();

    }
    private ProjectResponseDTO mapToProjectDTO(Project project) {

        ProjectResponseDTO projectResponse = modelMapper.map(project, ProjectResponseDTO.class);
       
        return projectResponse;
    }

    private User checkUserExistence(Long userId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new Exception("User Does Not Exists!")
        );
        return user;
    }

    private Project checkProjectExistence(Long projectId) throws Exception {

        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new Exception("Invalid Job-id!")
        );
        return project;
    }

    public Application checkProjectApplicationExistence(Long applicationId) throws Exception {

        Application application = appRepository.findById(applicationId).orElseThrow(
                () -> new Exception("Application Does Not Exists")
        );
        return application;
    }
	public List<ApplicationDTO> getAllApplicationsByProject(Long projectId) {
		
		List<Application> projectApplications = appRepository.findAllApplicationByProjectId(projectId);

        List<ApplicationDTO> AllApplications = projectApplications.stream().map((userApp) -> {
                    ApplicationDTO applicationDto = new ApplicationDTO();
                    // Fill the data
                    applicationDto.setApplicantionId(userApp.getId());
                    applicationDto.setStatus(userApp.getStatus());
                    applicationDto.setAppliedDate(userApp.getAppliedDate());
                    applicationDto.setProjectResponse(mapToProjectDTO(userApp.getProject()));
                    return applicationDto;
                }
        ).collect(Collectors.toList());


        return AllApplications;
		
	}
	public String rejectApplication(Long projectId, Long applicationId) {

		try {
        	
			Project project = checkProjectExistence(projectId);
			if(project == null) {
				return "Invalid Project Id";
			}
			Application application = checkProjectApplicationExistence(applicationId);

	        application.setStatus(RequestStatus.REJECTED.toString());

	        appRepository.save(application);

	        return "JApplication Reject Successfully";
		
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while rejecting", e);
		}
	}



}