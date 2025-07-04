package com.CapstoneProject.PartnerFinder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CapstoneProject.PartnerFinder.DTOs.PosterDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserDTO;
import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.PosterProfileInfo;
import com.CapstoneProject.PartnerFinder.model.Project;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.model.UserProfileInfo;
import com.CapstoneProject.PartnerFinder.repo.PosterProfileRepository;
import com.CapstoneProject.PartnerFinder.repo.PosterRepository;
import com.CapstoneProject.PartnerFinder.repo.ProjectRepository;
import com.CapstoneProject.PartnerFinder.repo.UserProfileRepository;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;

@Service
public class AdminService {
	
    @Autowired
    private UserRepository userRepo;
    

    @Autowired
    private UserProfileRepository userProfileRepo;

    @Autowired
    private PosterRepository posterRepo;
    
    @Autowired
    private PosterProfileRepository posterProfileRepo;
    
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<String> updateProjectStatus(Long id, String status) {
        Project project = projectRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        project.setStatus(status);
        projectRepo.save(project);
        return ResponseEntity.ok("Project status updated");
    }

    public ResponseEntity<String> deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

	public ResponseEntity<String> banUserPoster(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
		if(user != null) {
			UserProfileInfo userProfile = userProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
			user.setBanned(true);
			if(userProfile != null) {
				userProfile.setBan(true);
				userProfileRepo.save(userProfile);
			}
			userRepo.save(user);
			return ResponseEntity.ok("User ban Successful.");
		}
		Poster poster = posterRepo.findById(id).orElseThrow(() -> new RuntimeException("Poster Not Found!"));
		if(poster != null) {
			PosterProfileInfo posterProfile = posterProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
			if(posterProfile != null) {
				posterProfile.setBan(true);
				posterProfileRepo.save(posterProfile);
			}
			poster.setBanned(true);
			posterRepo.save(poster);
			return ResponseEntity.ok("Poster ban Successful.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Id!");
	}

	public ResponseEntity<String> unbanUserPoster(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
		if(user != null) {
			UserProfileInfo userProfile = userProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
			user.setBanned(false);
			if(userProfile != null) {
				userProfile.setBan(false);
				userProfileRepo.save(userProfile);
			}
			userRepo.save(user);
			return ResponseEntity.ok("User unban Successful.");
		}
		Poster poster = posterRepo.findById(id).orElseThrow(() -> new RuntimeException("Poster Not Found!"));
		if(poster != null) {
			PosterProfileInfo posterProfile = posterProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
			if(posterProfile != null) {
				posterProfile.setBan(false);
				posterProfileRepo.save(posterProfile);
			}
			poster.setBanned(false);
			posterRepo.save(poster);
			return ResponseEntity.ok("Poster unban Successful.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Id!");
	}

	public List<UserDTO> getAllUsers() {

	        List<User> dbUserList = userRepo.findAll();

	        return dbUserList.stream().map((user -> mapToDto(user))).collect(Collectors.toList());

	}
	
	private UserDTO mapToDto(User user) {
	        return modelMapper.map(user, UserDTO.class);
	}
	
	private PosterDTO mapToDto(Poster poster) {
	        return modelMapper.map(poster, PosterDTO.class);
	}

	public List<PosterDTO> getAllPosters() {
		List<Poster> list = posterRepo.findAll();
		return list.stream().map(poster -> mapToDto(poster)).collect(Collectors.toList());
	}
   
}