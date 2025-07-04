package com.CapstoneProject.PartnerFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.Qualification;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long>{

}