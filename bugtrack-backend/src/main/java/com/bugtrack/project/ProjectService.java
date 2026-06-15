package com.bugtrack.project;

import com.bugtrack.member.MemberRepository;
import com.bugtrack.member.ProjectMember;
import com.bugtrack.user.User;
import com.bugtrack.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final InviteCodeGenerator inviteCodeGenerator;
    private final MemberRepository memberRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          InviteCodeGenerator inviteCodeGenerator,
                          MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.inviteCodeGenerator = inviteCodeGenerator;
        this.memberRepository = memberRepository;
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
        projectRepository.save(saved);
        ProjectMember member = new ProjectMember();
        member.setProject(saved);
        member.setUser(currentUser);
        memberRepository.save(member);
        return saved;
    }

    public List<Project> getProjectsForCurrentUser() {
        User currentUser = getCurrentUser();
        return memberRepository.findByUserId(currentUser.getId())
                .stream()
                .map(m -> m.getProject())
                .collect(Collectors.toList());
    }

    public List<Project> getProjectsForCurrentUser() {
        User currentUser = getCurrentUser();
        return memberRepository.findByUserId(currentUser.getId())
                .stream()
                .map(m -> m.getProject())
                .collect(Collectors.toList());
    }

    public Project getProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
