package com.CapstoneProject.PartnerFinder.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile")
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileInfo {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id")
	    private Long id;
	    
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "user_id")
	    private User user;
	    
	    @Column(name = "photo_path")
	    private String photoPath;
	    
	    @Column(name = "github")
	    private String github;
	    
	    @Column(name = "linkedin")
	    private String linkedin;
	    
	    @Column(name = "bio")
	    private String bio;
	    
	    @Column(name = "isBan")
	    private boolean isBan = false;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getPhotoPath() {
			return photoPath;
		}

		public void setPhotoPath(String photoPath) {
			this.photoPath = photoPath;
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

		public boolean getIsBan() {
			return isBan;
		}

		public void setBan(boolean isBan) {
			this.isBan = isBan;
		}

		
	    
	    
}
