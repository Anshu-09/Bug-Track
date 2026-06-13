package com.bugtrack.issue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/projects/{id}/issues")
    public ResponseEntity<Issue> createIssue(@PathVariable UUID id,
                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(issueService.createIssue(id, body));
    }

    @GetMapping("/projects/{id}/issues")
    public ResponseEntity<List<Issue>> getIssues(@PathVariable UUID id,
                                                  @RequestParam(required = false) String q) {
        return ResponseEntity.ok(issueService.getIssues(id, q));
    }

    @PatchMapping("/issues/{id}/assign")
    public ResponseEntity<Issue> assignIssue(@PathVariable UUID id) {
        return ResponseEntity.ok(issueService.assignIssue(id));
    }

    @PatchMapping("/issues/{id}/status")
    public ResponseEntity<Issue> updateStatus(@PathVariable UUID id,
                                               @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(issueService.updateStatus(id, body.get("status")));
    }

    @PatchMapping("/issues/{id}/resolve")
    public ResponseEntity<Issue> resolveIssue(@PathVariable UUID id,
                                               @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(issueService.resolveIssue(id, body.get("branchLink")));
    }

    @DeleteMapping("/issues/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable UUID id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
