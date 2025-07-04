package com.CapstoneProject.PartnerFinder.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CapstoneProject.PartnerFinder.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProjectId(Long projectId);

    @Query("SELECT c FROM Comment c WHERE c.project.id = :projectId AND c.hide = false")
    List<Comment> findByProjectIdAndHideFalse(@Param("projectId") Long projectId);

}