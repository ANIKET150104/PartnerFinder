package com.CapstoneProject.PartnerFinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.model.Notification;
import com.CapstoneProject.PartnerFinder.repo.NotificationRepository;

@Service
public class NotificationService {
	
    @Autowired
    private NotificationRepository notificationRepo;

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepo.findByUserId(userId);
    }
}
