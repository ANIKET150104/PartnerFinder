package com.CapstoneProject.PartnerFinder.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.Poster;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long>{
	Optional<Poster> findByEmail(String email);
}
