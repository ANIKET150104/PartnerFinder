package com.CapstoneProject.PartnerFinder.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String departmentName;
	@Column
	private String techStack;
	@Column
	private String status;
	@Column
	private int requiredMembers;
	@Column
	private boolean isOpen = true;
	@Column
	private LocalDate deadline;
	@Column
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "project_user", 
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private Set<User> members = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "projectcategory_id")
	private ProjectCategory projectCategory;
	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "poster_id")
	private Poster poster;
	
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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


	public int getRequiredMembers() {
		return requiredMembers;
	}


	public void setRequiredMembers(int requiredMembers) {
		this.requiredMembers = requiredMembers;
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


	public ProjectCategory getProjectCategory() {
		return projectCategory;
	}


	public void setProjectCategory(ProjectCategory projectCategory) {
		this.projectCategory = projectCategory;
	}


	public Poster getPoster() {
		return poster;
	}


	public void setPoster(Poster poster) {
		this.poster = poster;
	}


	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public Set<User> getMembers() {
		return members;
	}

}
