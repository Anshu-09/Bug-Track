package com.bugtrack.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join/{code}")
    public ResponseEntity<ProjectMember> joinProject(@PathVariable String code) {
        return ResponseEntity.ok(memberService.joinProject(code));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<ProjectMember>> getMembers(@PathVariable UUID id) {
        return ResponseEntity.ok(memberService.getProjectMembers(id));
    }
}
