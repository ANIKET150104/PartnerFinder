package com.CapstoneProject.PartnerFinder.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.ProjectCategory;

@Repository
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long>{

	Optional<ProjectCategory> findByField(String field);
}
