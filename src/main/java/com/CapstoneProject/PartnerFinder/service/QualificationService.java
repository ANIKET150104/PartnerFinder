package com.CapstoneProject.PartnerFinder.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.repo.QualificationRepository;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;
import com.CapstoneProject.PartnerFinder.DTOs.QualificationDTO;
import com.CapstoneProject.PartnerFinder.model.Qualification;
import com.CapstoneProject.PartnerFinder.model.User;


@Service
public class QualificationService {
	
	private QualificationRepository qualificationRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    @Autowired
    public QualificationService(QualificationRepository qualificationRepository,
                                    ModelMapper modelMapper,
                                    UserRepository userRepository
    ) {
        this.qualificationRepository = qualificationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    public QualificationDTO createQualification(QualificationDTO qualificationDto, Long userId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new Exception("Invalid User")
        );

        // Set
        Qualification qualification = mapToQualification(qualificationDto);
        qualification.setUser(user);
        // insert
        Qualification savedQualification = qualificationRepository.save(qualification);
        return mapToQualificationDTO(savedQualification);
    }

    private Qualification mapToQualification(QualificationDTO qualificationDto) {

        return modelMapper.map(qualificationDto, Qualification.class);
    }

    private QualificationDTO mapToQualificationDTO(Qualification qualification) {
        return modelMapper.map(qualification, QualificationDTO.class);
    }

}
