package com.CapstoneProject.PartnerFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.UserProfileInfo;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileInfo, Long>{

}
