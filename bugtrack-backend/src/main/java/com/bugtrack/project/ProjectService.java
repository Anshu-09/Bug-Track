package com.bugtrack.project;

import com.bugtrack.user.User;
import com.bugtrack.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final InviteCodeGenerator inviteCodeGenerator;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          InviteCodeGenerator inviteCodeGenerator) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.inviteCodeGenerator = inviteCodeGenerator;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public Project createProject(String name) {
        User currentUser = getCurrentUser();
        Project project = new Project();
        project.setName(name);
        project.setCreatedBy(currentUser);
        Project saved = projectRepository.save(project);
        saved.setInviteCode(inviteCodeGenerator.generate(saved.getId()));
        return projectRepository.save(saved);
    }

    public Project getProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
