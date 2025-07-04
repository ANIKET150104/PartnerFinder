package com.CapstoneProject.PartnerFinder.DTOs;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private String password;
	private String department;
	private boolean profileVisible;
	private boolean isBanned;
	private String contactNumber;
	private String country;
	private String city;
	private String pinCode;
	private String state;
	private String street;
	private Date registerDate;
	private List<QualificationDTO> qualifications;
	private UserProfileResponse userProfile;
	private List<UserSkillDTO> userSkills;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isProfileVisible() {
		return profileVisible;
	}

	public void setProfileVisible(boolean profileVisible) {
		this.profileVisible = profileVisible;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public List<QualificationDTO> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<QualificationDTO> qualifications) {
		this.qualifications = qualifications;
	}

	public UserProfileResponse getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfileResponse userProfile) {
		this.userProfile = userProfile;
	}

	public List<UserSkillDTO> getUserSkills() {
		return userSkills;
	}

	public void setUserSkills(List<UserSkillDTO> userSkills) {
		this.userSkills = userSkills;
	}
}
