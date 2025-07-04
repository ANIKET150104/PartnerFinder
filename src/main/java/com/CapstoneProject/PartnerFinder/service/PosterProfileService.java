package com.CapstoneProject.PartnerFinder.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.CapstoneProject.PartnerFinder.DTOs.UserProfileDTO;
import com.CapstoneProject.PartnerFinder.DTOs.UserProfileResponse;
import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.PosterProfileInfo;
import com.CapstoneProject.PartnerFinder.repo.PosterProfileRepository;
import com.CapstoneProject.PartnerFinder.repo.PosterRepository;
import com.CapstoneProject.PartnerFinder.utlis.SavetoDisk;



@Service
public class PosterProfileService {

	public static String PATH_TO_SAVE_PROFILE_PHOTOS = "src/main/resources/profileImgs/";

	private PosterProfileRepository posterProfileRepository;
	private PosterRepository posterRepository;
	private ModelMapper modelMapper;
	
	public PosterProfileService(PosterProfileRepository posterProfileRepository, 
			PosterRepository posterRepository,
			ModelMapper modelMapper) {
		this.posterProfileRepository = posterProfileRepository;
		this.posterRepository = posterRepository;
		this.modelMapper = modelMapper;
	}
	
	public UserProfileResponse createPosterProfile(UserProfileDTO userProfileDto, Long posterId) throws Exception {
		Poster poster = posterRepository.findById(posterId).orElseThrow(() -> new Exception("Invalid Id"));
		
		MultipartFile profilePhoto = userProfileDto.getPhoto();
		
		Path profilePhotoFullPath = Paths.get(PATH_TO_SAVE_PROFILE_PHOTOS + profilePhoto.getOriginalFilename());
		
		SavetoDisk.saveFile(profilePhoto, profilePhotoFullPath);
		
        PosterProfileInfo posterProfile = new PosterProfileInfo();

        posterProfile.setPoster(poster);
        posterProfile.setBio(userProfileDto.getBio());
        posterProfile.setGithub(userProfileDto.getGithub());
        posterProfile.setLinkedin(userProfileDto.getLinkedin());
        posterProfile.setPhotoPath(profilePhotoFullPath.toString());
       
        return mapTOResponse(posterProfileRepository.save(posterProfile));
	}
	
    private UserProfileResponse mapTOResponse(PosterProfileInfo posterProfileInfo) {

        return modelMapper.map(posterProfileInfo, UserProfileResponse.class);
    }
	
}
