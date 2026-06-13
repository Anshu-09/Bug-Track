package com.bugtrack.activity;

import org.springframework.stereotype.Component;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Component
public class ActivityStack {

    private static final int MAX_SIZE = 20;

    public List<ActivityLog> getTop20(List<ActivityLog> logs) {
        Deque<ActivityLog> deque = new ArrayDeque<>();
        for (ActivityLog log : logs) {
            deque.push(log);
            if (deque.size() > MAX_SIZE) deque.pollLast();
        }
        return new ArrayList<>(deque);
    }
}
