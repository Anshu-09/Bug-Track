package com.bugtrack.activity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/{id}/activity")
    public ResponseEntity<List<ActivityLog>> getActivity(@PathVariable UUID id) {
        return ResponseEntity.ok(activityService.getRecentActivity(id));
    }
}
