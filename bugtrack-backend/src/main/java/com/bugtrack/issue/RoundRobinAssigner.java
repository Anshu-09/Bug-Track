package com.bugtrack.issue;

import com.bugtrack.member.ProjectMember;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class RoundRobinAssigner {

    public ProjectMember assign(List<ProjectMember> members, int callCount) {
        if (members.isEmpty()) throw new RuntimeException("No members in project");
        Queue<ProjectMember> queue = new LinkedList<>(members);
        ProjectMember assigned = null;
        for (int i = 0; i <= callCount % members.size(); i++) {
            assigned = queue.poll();
            queue.offer(assigned);
        }
        return assigned;
    }
}
