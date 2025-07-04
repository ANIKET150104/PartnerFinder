package com.CapstoneProject.PartnerFinder.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.DTOs.UserSkillDTO;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.model.UserSkill;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;
import com.CapstoneProject.PartnerFinder.repo.UserSkillRepository;


@Service
public class UserSkillService {

	    private UserSkillRepository userSkillRepository;
	    private UserRepository userRepository;
	    private ModelMapper modelMapper;

	    public UserSkillService(UserSkillRepository userSkillRepository,
	                                    UserRepository userRepository,
	                                    ModelMapper modelMapper) {
	        this.userSkillRepository = userSkillRepository;
	        this.userRepository = userRepository;
	        this.modelMapper = modelMapper;
	    }


	    public UserSkillDTO createSkill(UserSkillDTO userSkillDto, Long userId) throws Exception {

	        User user = userRepository.findById(userId).orElseThrow(
	                () -> new Exception("Invalid User")
	        );

	        UserSkill userSkill = mapToEntity(userSkillDto);
	        userSkill.setUser(user);
	        UserSkill db = userSkillRepository.save(userSkill);
	        return mapToDto(db);
	    }


	    private UserSkillDTO mapToDto(UserSkill userSkill) {

	        return modelMapper.map(userSkill, UserSkillDTO.class);
	    }

	    private UserSkill mapToEntity(UserSkillDTO userSkillDto) {

	        return modelMapper.map(userSkillDto, UserSkill.class);
	    }


}
