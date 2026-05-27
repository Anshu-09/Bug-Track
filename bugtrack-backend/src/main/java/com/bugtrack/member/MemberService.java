package com.bugtrack.member;

import com.bugtrack.project.Project;
import com.bugtrack.project.ProjectRepository;
import com.bugtrack.user.User;
import com.bugtrack.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public MemberService(MemberRepository memberRepository,
                         ProjectRepository projectRepository,
                         UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ProjectMember joinProject(String inviteCode) {
        Project project = projectRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Invalid invite code"));
        User currentUser = getCurrentUser();
        if (memberRepository.existsByProjectIdAndUserId(project.getId(), currentUser.getId())) {
            throw new RuntimeException("Already a member of this project");
        }
        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(currentUser);
        return memberRepository.save(member);
    }

    public List<ProjectMember> getProjectMembers(java.util.UUID projectId) {
        return memberRepository.findByProjectId(projectId);
    }
}
