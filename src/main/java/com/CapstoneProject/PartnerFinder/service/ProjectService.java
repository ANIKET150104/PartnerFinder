package com.CapstoneProject.PartnerFinder.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CapstoneProject.PartnerFinder.DTOs.ProjectDTO;
import com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO;
import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.Project;
import com.CapstoneProject.PartnerFinder.model.ProjectCategory;
import com.CapstoneProject.PartnerFinder.repo.PosterRepository;
import com.CapstoneProject.PartnerFinder.repo.ProjectCategoryRepository;
import com.CapstoneProject.PartnerFinder.repo.ProjectRepository;



@Service
public class ProjectService {
	
	private ProjectRepository projectRepository;
    private ModelMapper modelMapper;
    private PosterRepository posterRepository;
    private ProjectCategoryRepository projectCategoryRepository;

    public ProjectService(
    		ProjectRepository projectRepository,
    	    ModelMapper modelMapper,
    	    PosterRepository posterRepository,
    	    ProjectCategoryRepository projectCategoryRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.posterRepository = posterRepository;
        this.projectCategoryRepository = projectCategoryRepository;
    }

    public String createProject(ProjectDTO projectDto, Long posterId) throws IOException {

        Poster poster = checkPosterExist(posterId);
        
        ProjectCategory projectCategory = checkProjectCategoryExist(projectDto.getProjectCategory());


        Project project = mapToProject(projectDto);
        project.setPoster(poster);
        project.setProjectCategory(projectCategory);
        projectRepository.save(project);

        return "Project Created Successfully";
    }

    public List<ProjectResponseDTO> findByAllProjectsByPosterId(Long id) {
    	Poster poster = checkPosterExist(id);
    	
    	if(poster == null) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poster Not Found");
    	}
         return projectRepository.findByAllProjectsByPosterId(id);
   }

    public List<ProjectResponseDTO> getAllProjects() {

        return projectRepository.findByAllProjects();
    }

	public String deleteProject(Long posterId, Long projectId) {

		try {
			Poster poster = checkPosterExist(posterId);
			Project project = checkProjectExist(projectId);
			if (poster != null && poster.getId().equals(posterId)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Poster Id For Project");
			}
			project.setPoster(null);
			project.setProjectCategory(null);
			projectRepository.delete(project);
			return "Project Deleted Successfully";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poster Not Found", e);
		}

	}

    public ProjectResponseDTO getProjectByProjectId(long projectId) {

        Project project;
		try {
			project = getProjectById(projectId);
			 return mapToProjectDto(project);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found", e);
		}
    }
    
    public Project getProjectById(long projectId) throws Exception {
    	Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new Exception("Invalid UserId!")
        );
    	return project;
    }

    public List<ProjectResponseDTO> searchProject(String projectCategory, Boolean isOpen, String techStack) {


        return projectRepository.searchProjects(projectCategory, isOpen, techStack);
    }


    private Poster checkPosterExist(long posterId) {

        Poster poster;
		try {
			poster = posterRepository.findById(posterId).orElseThrow(
			        () -> new Exception("Invalid User")
			);
			return poster;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poster Not Found", e);
		}
    }

    private ProjectCategory checkProjectCategoryExist(String field) {
        ProjectCategory projectCategory;
		try {
			projectCategory = projectCategoryRepository.findByField(field).orElseThrow(
			        () -> new Exception("Project-Category Doesn't Exists")
			);

	        return projectCategory;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found", e);
		}
    }


    private Project checkProjectExist(long projectId) throws Exception {

        return projectRepository.findById(projectId).orElseThrow(
                () -> new Exception("Project Not Found")
        );

    }
    private Project mapToProject(ProjectDTO projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
        return project;
    }

    private ProjectResponseDTO mapToProjectDto(Project project) {

        ProjectResponseDTO projectResponse = modelMapper.map(project, ProjectResponseDTO.class);
        return projectResponse;
    }

	public List<ProjectResponseDTO> getProjectsByMemberEmail(String email) {
		return projectRepository
				.findAll()
				.stream()
				.filter(p -> p.getMembers()
				.stream()
				.anyMatch(u -> u.getEmail()
						.equalsIgnoreCase(email)))
				.map(this::mapToProjectDto)
				.collect(Collectors.toList());
	}

}