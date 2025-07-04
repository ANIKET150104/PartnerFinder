package com.CapstoneProject.PartnerFinder.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO;
import com.CapstoneProject.PartnerFinder.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query("SELECT NEW com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO(j.id,j.title,j.departmentName,j.description,j.techStack,"
			+ "j.isOpen,j.deadline,j.projectCategory.field,j.status,j.poster.id) " + "FROM Project j ")
	List<ProjectResponseDTO> findByAllProjects();

	@Query("SELECT NEW com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO(j.id,j.title,j.departmentName,j.description,j.techStack,"
			+ "j.status,j.requiredMembers,j.isOpen,j.deadline,j.createdAt,j.projectCategory.field,j.poster.id) "
			+ "FROM Project j " + "WHERE j.poster.id = :posterId")
	List<ProjectResponseDTO> findByAllProjectsByPosterId(@Param("posterId") Long posterId);

	@Query("SELECT NEW com.CapstoneProject.PartnerFinder.DTOs.ProjectResponseDTO("
			+ "j.id, j.title, j.departmentName, j.description, j.techStack,"
			+ "j.isOpen, j.deadline, j.status, j.projectCategory.field, j.poster.id) " + "FROM Project j "
			+ "WHERE (:projectCategory IS NULL OR j.projectCategory.field LIKE %:projectCategory%) "
			+ "AND (:isOpen IS NULL OR j.isOpen = :isOpen) "
			+ "AND (:techStack IS NULL OR j.techStack LIKE %:techStack%)")
	List<ProjectResponseDTO> searchProjects(@Param("projectCategory") String projectCategory,
			@Param("isOpen") Boolean isOpen, @Param("techStack") String techStack);

}
