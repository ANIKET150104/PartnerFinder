package com.CapstoneProject.PartnerFinder.DTOs;

import java.time.LocalDateTime;


public class ApplicationDTO {
	
    private ProjectResponseDTO projectResponse;
    private Long applicantionId;
    private String status;
	private LocalDateTime appliedDate;
	
	public ProjectResponseDTO getProjectResponse() {
		return projectResponse;
	}
	public void setProjectResponse(ProjectResponseDTO projectResponse) {
		this.projectResponse = projectResponse;
	}
	public Long getApplicantionId() {
		return applicantionId;
	}
	public void setApplicantionId(Long applicantionId) {
		this.applicantionId = applicantionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(LocalDateTime appliedDate) {
		this.appliedDate = appliedDate;
	}
}

