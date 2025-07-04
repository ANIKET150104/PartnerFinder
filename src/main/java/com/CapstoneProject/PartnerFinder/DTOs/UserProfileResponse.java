package com.CapstoneProject.PartnerFinder.DTOs;

import java.nio.file.Paths;

public class UserProfileResponse {
	
	private Long id;
    private String photoPath;
    private String github;
    private String linkedin;
    private String bio;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

    public String getPhotoPath() {

        return Paths.get(photoPath).getFileName().toString();
    }

}
