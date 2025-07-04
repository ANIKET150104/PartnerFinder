 package com.CapstoneProject.PartnerFinder.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {
	
	private Long id;
	private String title;
	private String departmentName;
	private String description;
	private Integer requiredMembers;
	private String techStack;
	private String status;
	private boolean isOpen;
	private LocalDate deadline;
	private LocalDateTime createdAt;
	private String projectCategory;
	private Long posterId;
	
	public ProjectResponseDTO(
		    Long id,
		    String title,
		    String departmentName,
		    String description,
		    String techStack,
		    String status,
		    Integer requiredMembers,
		    boolean isOpen,
		    LocalDate deadline,
		    LocalDateTime createdAt,
		    String projectCategory,
		    Long posterId
		) {
		    this.id = id;
		    this.title = title;
		    this.departmentName = departmentName;
		    this.description = description;
		    this.techStack = techStack;
		    this.status = status;
		    this.requiredMembers = requiredMembers;
		    this.isOpen = isOpen;
		    this.deadline = deadline;
		    this.createdAt = createdAt;
		    this.projectCategory = projectCategory;
		    this.posterId = posterId;
		}
	public ProjectResponseDTO(
		    Long id,
		    String title,
		    String departmentName,
		    String description,
		    String techStack,
		    boolean isOpen,
		    LocalDate deadline,
		    String projectCategory,
		    String status,
		    Long posterId
		) {
		    this.id = id;
		    this.title = title;
		    this.departmentName = departmentName;
		    this.description = description;
		    this.techStack = techStack;
		    this.isOpen = isOpen;
		    this.deadline = deadline;
		    this.projectCategory = projectCategory;
		    this.status = status;
		    this.posterId = posterId;
		}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getRequiredMembers() {
		return requiredMembers;
	}
	public void setRequiredMembers(Integer requiredMembers) {
		this.requiredMembers = requiredMembers;
	}
	public String getTechStack() {
		return techStack;
	}
	public void setTechStack(String techStack) {
		this.techStack = techStack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public LocalDate getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getProjectCategory() {
		return projectCategory;
	}
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
	public Long getPosterId() {
		return posterId;
	}
	public void setPosterId(Long posterId) {
		this.posterId = posterId;
	}
}
