package com.bugtrack.issue;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class IssueSorter {

    private static final Map<String, Integer> SEVERITY_ORDER = new HashMap<>();

    static {
        SEVERITY_ORDER.put("HIGH", 1);
        SEVERITY_ORDER.put("MEDIUM", 2);
        SEVERITY_ORDER.put("LOW", 3);
    }

    public List<Issue> sort(List<Issue> issues) {
        PriorityQueue<Issue> pq = new PriorityQueue<>(Comparator
                .comparingInt((Issue i) -> SEVERITY_ORDER.getOrDefault(i.getSeverity(), 99))
                .thenComparing((i1, i2) -> i2.getCreatedAt().compareTo(i1.getCreatedAt())));
        pq.addAll(issues);
        List<Issue> sorted = new ArrayList<>();
        while (!pq.isEmpty()) sorted.add(pq.poll());
        return sorted;
    }
}
