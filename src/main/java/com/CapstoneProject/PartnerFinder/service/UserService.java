package com.CapstoneProject.PartnerFinder.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.CapstoneProject.PartnerFinder.DTOs.UserDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileDTO;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.model.UserProfileInfo;
import com.CapstoneProject.PartnerFinder.model.UserRole;
import com.CapstoneProject.PartnerFinder.repo.UserProfileRepository;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;


@Service
public class UserService {

	private UserProfileRepository userProfileRepository;
	private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, 
    		ModelMapper modelMapper, 
    		UserProfileRepository userProfileRepository, 
    		PasswordEncoder passwordEncoder
    		) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
    	
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
    	UserRole userRole = new UserRole();
    	userRole.setName("ROLE_COLLABORATOR");
    	userRole.setUser(user);

    	Set<UserRole> roles = new HashSet<>();
    	roles.add(userRole);

    	user.setRoles(roles);

        user.setRegisterDate(new Date());
        return userRepository.save(user);
    }

    public List<UserProfileDTO> getAllUsers() {

        List<UserProfileInfo> dbUserList = userProfileRepository.findAll().stream()
                .filter(u -> !u.getIsBan())
                .collect(Collectors.toList());;

        return dbUserList.stream().map((user -> mapToDto(user))).collect(Collectors.toList());

    }
   
    public UserDTO getUserById(Long userId) throws Exception {

        User user = checkUserIfExist(userId);

        return mapToDto(user);
    }
    
    public Optional<User> getCollaboratorById(Long userId) {
    	return userRepository.findById(userId);
    }

    private UserDTO mapToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    private UserProfileDTO mapToDto(UserProfileInfo userProfile) {
        return modelMapper.map(userProfile, UserProfileDTO.class);
    }
    private User checkUserIfExist(Long userId) throws Exception {

        return userRepository.findById(userId).orElseThrow(
                () -> new Exception("Invalid User")
        );
    }


}
