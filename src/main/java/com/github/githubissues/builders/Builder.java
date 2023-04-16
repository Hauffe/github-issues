package com.github.githubissues.builders;

import com.github.githubissues.model.Contributor;
import com.github.githubissues.model.Issue;
import com.github.githubissues.model.User;

import java.util.List;

public interface Builder {
    void setUser(User user);
    void setRepository(String repository);
    void setContributors(List<Contributor> contributors);
    void setIssues(List<Issue> issues);
}
