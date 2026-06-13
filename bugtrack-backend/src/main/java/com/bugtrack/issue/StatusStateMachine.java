package com.bugtrack.issue;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class StatusStateMachine {

    private static final Map<String, List<String>> TRANSITIONS = new HashMap<>();

    static {
        TRANSITIONS.put("OPEN", List.of("IN_PROGRESS"));
        TRANSITIONS.put("IN_PROGRESS", List.of("RESOLVED", "OPEN"));
        TRANSITIONS.put("RESOLVED", List.of());
    }

    public void validate(String current, String next) {
        List<String> allowed = TRANSITIONS.getOrDefault(current, List.of());
        if (!allowed.contains(next)) {
            throw new RuntimeException("Invalid status transition: " + current + " → " + next);
        }
    }
}
