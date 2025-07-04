package com.CapstoneProject.PartnerFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.UserSkill;


@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
}
