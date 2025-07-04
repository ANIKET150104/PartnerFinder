package com.CapstoneProject.PartnerFinder.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.CapstoneProject.PartnerFinder.DTOs.UserProfileDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileResponse;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.model.UserProfileInfo;
import com.CapstoneProject.PartnerFinder.repo.UserProfileRepository;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;
import com.CapstoneProject.PartnerFinder.utlis.SavetoDisk;


@Service
public class UserProfileService {

    public static String PATH_TO_SAVE_PROFILE_PHOTOS = "src/main/resources/profileImgs/";

	private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    private ModelMapper modelMapper;

    public UserProfileService(UserRepository userRepository,
                                      UserProfileRepository userProfileRepository,
                                      ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    public UserProfileResponse createUserProfile(UserProfileDTO userProfileDto, Long userId)
            throws Exception {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new Exception("Invalid User")
        );


        MultipartFile profilePhoto = userProfileDto.getPhoto();
       

        Path profilePhotoFullPath = Paths.get(PATH_TO_SAVE_PROFILE_PHOTOS + profilePhoto.getOriginalFilename());

        SavetoDisk.saveFile(profilePhoto, profilePhotoFullPath);


        UserProfileInfo userProfile = new UserProfileInfo();

        userProfile.setUser(user);
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setGithub(userProfileDto.getGithub());
        userProfile.setLinkedin(userProfileDto.getLinkedin());
        userProfile.setPhotoPath(profilePhotoFullPath.toString());
       


        return mapTOResponse(userProfileRepository.save(userProfile));
    }


    private UserProfileResponse mapTOResponse(UserProfileInfo userProfileInfo) {

        return modelMapper.map(userProfileInfo, UserProfileResponse.class);
    }

}
