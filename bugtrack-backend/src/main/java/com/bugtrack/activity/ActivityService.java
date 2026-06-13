package com.bugtrack.activity;

import com.bugtrack.project.Project;
import com.bugtrack.project.ProjectRepository;
import com.bugtrack.user.User;
import com.bugtrack.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityStack activityStack;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ActivityService(ActivityRepository activityRepository,
                           ActivityStack activityStack,
                           ProjectRepository projectRepository,
                           UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.activityStack = activityStack;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void logAction(UUID projectId, String action) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User currentUser = getCurrentUser();
        ActivityLog log = new ActivityLog();
        log.setProject(project);
        log.setUser(currentUser);
        log.setAction(action);
        activityRepository.save(log);
    }

    public List<ActivityLog> getRecentActivity(UUID projectId) {
        List<ActivityLog> logs = activityRepository
                .findTop20ByProjectIdOrderByCreatedAtDesc(projectId);
        return activityStack.getTop20(logs);
    }
}
