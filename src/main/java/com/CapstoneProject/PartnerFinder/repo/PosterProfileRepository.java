package com.CapstoneProject.PartnerFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.PosterProfileInfo;

@Repository
public interface PosterProfileRepository extends JpaRepository<PosterProfileInfo, Long>{

}
