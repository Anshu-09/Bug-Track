package com.bugtrack.issue;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IssueSearchEngine {

    public List<Issue> search(List<Issue> issues, String keyword) {
        String pattern = keyword.toLowerCase();
        return issues.stream()
                .filter(i -> kmpSearch(i.getTitle().toLowerCase(), pattern)
                        || kmpSearch(i.getDescription() != null ? i.getDescription().toLowerCase() : "", pattern))
                .collect(Collectors.toList());
    }

    private boolean kmpSearch(String text, String pattern) {
        if (pattern.isEmpty()) return true;
        int[] lps = computeLPS(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) { i++; j++; }
            if (j == pattern.length()) return true;
            else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return false;
    }

    private int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0, i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) { lps[i++] = ++len; }
            else if (len != 0) { len = lps[len - 1]; }
            else { lps[i++] = 0; }
        }
        return lps;
    }
}
