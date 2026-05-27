package com.bugtrack.project;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class InviteCodeGenerator {

    private static final String BASE36_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;

    public String generate(UUID projectId) {
        long hash = Math.abs(projectId.hashCode());
        StringBuilder code = new StringBuilder();
        while (code.length() < CODE_LENGTH) {
            code.append(BASE36_CHARS.charAt((int)(hash % 36)));
            hash /= 36;
            if (hash == 0) hash = Math.abs(UUID.randomUUID().hashCode());
        }
        return code.toString();
    }
}
