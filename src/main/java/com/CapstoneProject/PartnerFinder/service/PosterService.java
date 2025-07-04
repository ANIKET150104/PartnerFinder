package com.CapstoneProject.PartnerFinder.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.DTOs.PosterDTO;
import com.CapstoneProject.PartnerFinder.model.Application;
import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.PosterRole;
import com.CapstoneProject.PartnerFinder.model.Project;
import com.CapstoneProject.PartnerFinder.model.RequestStatus;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.repo.ApplicationRepository;
import com.CapstoneProject.PartnerFinder.repo.PosterRepository;
import com.CapstoneProject.PartnerFinder.repo.ProjectRepository;

@Service
public class PosterService {

	private PosterRepository posterRepository;
	private ApplicationRepository applicationRepository;
	private ProjectRepository projectRepository;
	private ApplicationService applicationService;
	private ModelMapper modelMapper;
	private PasswordEncoder passwordEncoder;

    public PosterService(PosterRepository posterRepository, 
    		ApplicationRepository applicationRepository, 
    		ProjectRepository projectRepository,
    		ApplicationService applicationService,
    		ModelMapper modelMapper,
    		PasswordEncoder passwordEncoder
    		) {
        this.posterRepository = posterRepository;
        this.applicationRepository = applicationRepository;
        this.projectRepository = projectRepository;
        this.applicationService = applicationService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public Project approveApplication(Long applicationId) throws Exception {
    	Application application = applicationService.checkProjectApplicationExistence(applicationId);
    	Project project = application.getProject();
    	User user = application.getUser();
    	
    	application.setStatus(RequestStatus.APPROVED.toString());
    	applicationRepository.save(application);
    	applicationRepository.delete(application);
    	
    	if(project.getMembers()==null) {
    		project.setMembers(new HashSet<>());
    	}project.getMembers().add(user);
    	
    	Integer remaining = project.getRequiredMembers();
    	if(remaining != null && remaining > 0) {
    		project.setRequiredMembers(remaining - 1);
    		project.setOpen(false);
    	}
    	projectRepository.save(project); 
    	return project;
    }
    
    public List<PosterDTO> findAllPosters() {

        List<Poster> list = posterRepository.findAll().stream()
                .filter(p -> !p.isBanned())
                .collect(Collectors.toList());;
                
        return list.stream().map(poster -> mapToDto(poster)).collect(Collectors.toList());
    }
    public Optional<Poster> getPosterById(Long posterId) {
    	return posterRepository.findById(posterId);
    }

    private PosterDTO mapToDto(Poster poster) {
        return modelMapper.map(poster, PosterDTO.class);
    }
    public Poster createPoster(Poster poster) {

        String encodedPassword = passwordEncoder.encode(poster.getPassword());
        poster.setPassword(encodedPassword);
        
    	PosterRole posterRole = new PosterRole();
    	posterRole.setName("ROLE_POSTER");
    	posterRole.setPoster(poster);

    	Set<PosterRole> roles = new HashSet<>();
    	roles.add(posterRole);

    	poster.setRoles(roles);

        return posterRepository.save(poster);
    }
	public Optional<Poster> getUserByEmail(String username) {
		return posterRepository.findByEmail(username);
	}
}
