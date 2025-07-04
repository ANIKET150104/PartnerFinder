package com.CapstoneProject.PartnerFinder.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.DTOs.PosterApplicationDTO;
import com.CapstoneProject.PartnerFinder.model.Application;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

	@Query("from Application j where j.user.id=:userId")
	List<Application> findAllApplicationByUserId(@Param("userId") Long userId);

	@Query("from Application j where j.project.id=:projectId")
	List<Application> findAllApplicationByProjectId(@Param("projectId") Long projectId);

	@Query("SELECT NEW com.CapstoneProject.PartnerFinder.DTOs.PosterApplicationDTO(j.departmentName, "
			+ "j.title, j.projectCategory.field, j.poster.firstName, ja.id, ja.status) " + "FROM Project j "
			+ "JOIN Application ja ON ja.project.id = j.id " + "WHERE j.poster.id =:posterId")
	List<PosterApplicationDTO> findAllApplicationForPoster(@Param("posterId") Long posterId);

	@Query("SELECT NEW com.CapstoneProject.PartnerFinder.DTOs.PosterApplicationDTO(j.departmentName, "
			+ "j.title, j.projectCategory.field, j.poster.firstName, ja.id, ja.status) " + "FROM Project j "
			+ "JOIN Application ja ON ja.project.id = j.id ")
	List<PosterApplicationDTO> findAllApplication();
}
