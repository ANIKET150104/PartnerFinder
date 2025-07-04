package com.CapstoneProject.PartnerFinder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.model.Comment;
import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.Project;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.repo.CommentRepository;

@Service
public class CommentService {
	
    
    private CommentRepository commentRepo;
    private ProjectService projectService;
    private UserService userService;
    private PosterService posterService;
    

    public CommentService(CommentRepository commentRepo, ProjectService projectService, UserService userService,
			PosterService posterService) {
		this.commentRepo = commentRepo;
		this.projectService = projectService;
		this.userService = userService;
		this.posterService = posterService;
	}

	public Comment addComment(Comment comment, Long projectId, Long actorId) {
		try {
			Project project = projectService.getProjectById(projectId);
			comment.setProject(project);
			comment.setCreatedAt(LocalDateTime.now());
			Optional<Poster> posterOptional = posterService.getPosterById(actorId);
			if (posterOptional.isPresent()) {
				Poster poster = posterOptional.get();
				comment.setPoster(poster);
			}
			Optional<User> userOptional = userService.getCollaboratorById(actorId);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				comment.setUser(user);
			}
			if (userOptional.isEmpty() && posterOptional.isEmpty()) {
				throw new RuntimeException("Invalid Id. Must Sign Up First");
			}
			return commentRepo.save(comment);
		} catch (Exception e) {
			throw new RuntimeException("Invalid ProjectId");
		}

	}

	public Comment hideComment(Long commentId, Long posterId) throws Exception {
		Optional<Poster> poster = posterService.getPosterById(posterId);
		if (poster.isPresent()) {
			Comment comment = commentRepo.findById(commentId)
					.orElseThrow(() -> new RuntimeException("Comment not found"));

			boolean isPosterOfProject = comment.getProject().getPoster().getId().equals(posterId);
			if (isPosterOfProject) {
				comment.setHide(true);
				return commentRepo.save(comment);
			}
		}
		throw new Exception("Invalid Id");
	}

	public List<Comment> getAllCommentsByProject(Long projectId) {
		return commentRepo.findByProjectId(projectId);
	}

	public List<Comment> getVisibleComments(Long projectId) {
		return commentRepo.findByProjectIdAndHideFalse(projectId);
	}

	public void deleteComment(Long commentId, String username) throws Exception {
		Comment comment;
		try {
			comment = commentRepo.findById(commentId).orElseThrow(() -> new Exception("Comment not found"));
			Optional<Poster> requesterOptional = posterService.getUserByEmail(username);

			if (requesterOptional.isPresent()) {
				Poster requester = requesterOptional.get();
				boolean isAdmin = requester.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
				if (isAdmin) {
					commentRepo.delete(comment);
				}
			}
			boolean isCommentAuthor = comment.getUser().getEmail().equals(username);
			boolean isPosterOfProject = comment.getProject().getPoster().getEmail().equals(username);

			if (isCommentAuthor || isPosterOfProject) {
				commentRepo.delete(comment);
			} else {
				throw new Exception("You do not have permission to delete this comment");
			}
		} catch (Exception e) {
			throw new Exception("Comment Not Found!");
		}

	}
}
