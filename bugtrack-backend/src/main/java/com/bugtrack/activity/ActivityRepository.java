package com.bugtrack.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<ActivityLog, UUID> {
    List<ActivityLog> findTop20ByProjectIdOrderByCreatedAtDesc(UUID projectId);
}
