package com.CapstoneProject.PartnerFinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CapstoneProject.PartnerFinder.model.Comment;
import com.CapstoneProject.PartnerFinder.service.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "REST APIs for Comments Resource")
public class CommentController {
	
    @Autowired
    private CommentService commentService;

    @PostMapping("/project/{projectId}/actor/{actorId}")
    public Comment addComment(@RequestBody Comment comment, @PathVariable Long projectId, @PathVariable Long actorId) {
        return commentService.addComment(comment, projectId, actorId);
    }
    
    @PutMapping("/hide/{commentId}/poster/{posterId}")
    @PreAuthorize("hasRole('POSTER')")
    public ResponseEntity<?> hideComment(@PathVariable Long commentId, @PathVariable Long posterId) {
        Comment updatedComment;
		try {
			updatedComment = commentService.hideComment(commentId, posterId);
			 return ResponseEntity.ok(updatedComment);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
		}
  
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        String username = authentication.getName();

        try {
            commentService.deleteComment(commentId, username);
            return ResponseEntity.ok("Comment deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


    @GetMapping("/project/{projectId}")
    public List<Comment> visibleCommentsByProjectId(@PathVariable Long projectId){
    	return commentService.getVisibleComments(projectId);
    }

    @GetMapping("/project/{projectId}/all")
    @PreAuthorize("hasRole('POSTER') or hasRole('ADMIN')")
    public List<Comment> getByProject(@PathVariable Long projectId) {
        return commentService.getAllCommentsByProject(projectId);
    }
}
