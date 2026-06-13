package com.bugtrack.issue;

import com.bugtrack.member.MemberRepository;
import com.bugtrack.project.Project;
import com.bugtrack.project.ProjectRepository;
import com.bugtrack.user.User;
import com.bugtrack.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final IssueSorter issueSorter;
    private final IssueSearchEngine issueSearchEngine;
    private final StatusStateMachine statusStateMachine;

    public IssueService(IssueRepository issueRepository,
                        ProjectRepository projectRepository,
                        UserRepository userRepository,
                        MemberRepository memberRepository,
                        IssueSorter issueSorter,
                        IssueSearchEngine issueSearchEngine,
                        StatusStateMachine statusStateMachine) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.issueSorter = issueSorter;
        this.issueSearchEngine = issueSearchEngine;
        this.statusStateMachine = statusStateMachine;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Project getProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    private void validateMember(UUID projectId, UUID userId) {
        if (!memberRepository.existsByProjectIdAndUserId(projectId, userId)) {
            throw new RuntimeException("User is not a member of this project");
        }
    }

    public Issue createIssue(UUID projectId, Map<String, String> body) {
        User currentUser = getCurrentUser();
        Project project = getProject(projectId);
        validateMember(projectId, currentUser.getId());

        Issue issue = new Issue();
        issue.setTitle(body.get("title"));
        issue.setDescription(body.get("description"));
        issue.setSeverity(body.get("severity"));
        issue.setCodeRef(body.get("codeRef"));
        issue.setProject(project);
        issue.setPostedBy(currentUser);
        return issueRepository.save(issue);
    }

    public List<Issue> getIssues(UUID projectId, String keyword) {
        List<Issue> issues = issueRepository.findByProjectId(projectId);
        if (keyword != null && !keyword.isEmpty()) {
            issues = issueSearchEngine.search(issues, keyword);
        }
        return issueSorter.sort(issues);
    }

    @Transactional
    public Issue assignIssue(UUID issueId) {
        User currentUser = getCurrentUser();
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        validateMember(issue.getProject().getId(), currentUser.getId());
        statusStateMachine.validate(issue.getStatus(), "IN_PROGRESS");
        issue.setAssignedTo(currentUser);
        issue.setStatus("IN_PROGRESS");
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue updateStatus(UUID issueId, String newStatus) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        statusStateMachine.validate(issue.getStatus(), newStatus);
        issue.setStatus(newStatus);
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue resolveIssue(UUID issueId, String branchLink) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        statusStateMachine.validate(issue.getStatus(), "RESOLVED");
        issue.setBranchLink(branchLink);
        issue.setStatus("RESOLVED");
        issue.setResolvedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    public void deleteIssue(UUID issueId) {
        issueRepository.deleteById(issueId);
    }
}
