package com.CapstoneProject.PartnerFinder.DTOs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PosterApplicationDTO {

	private String departmentName;
	private String projectTitle;
	private String projectCategory;
	private String collaboratorName;
	private Long applicationId;
	private String status;

	public PosterApplicationDTO(String departmentName, String projectTitle, String projectCategory,
			String collaboratorName, Long applicationId, String status) {
		this.departmentName = departmentName;
		this.projectTitle = projectTitle;
		this.projectCategory = projectCategory;
		this.collaboratorName = collaboratorName;
		this.applicationId = applicationId;
		this.status = status;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getCollaboratorName() {
		return collaboratorName;
	}

	public void setCollaboratorName(String collaboratorName) {
		this.collaboratorName = collaboratorName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}