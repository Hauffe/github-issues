package com.github.githubissues.builders;

import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.dto.ContributorDto;
import com.github.githubissues.dto.IssueDto;
import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.model.Contributor;
import com.github.githubissues.model.Issue;
import com.github.githubissues.model.Label;
import com.github.githubissues.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RepositoryBuilder implements Builder{

    private String user;
    private String repositoryName;
    private List<IssueDto> issues;
    private List<ContributorDto> contributors;
    private UrlCaller urlCaller;

    @Autowired
    public RepositoryBuilder(UrlCaller urlCaller) {
        this.urlCaller = urlCaller;
        this.issues = new ArrayList<>();
        this.contributors = new ArrayList<>();

    }

    @Override
    public void setUser(User user) {
        this.user = user.getName();
    }

    @Override
    public void setRepository(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    @Override
    public void setContributors(List<Contributor> contributors) {
        this.contributors.clear();
        contributors.forEach(contributor -> {
            User user = (User) urlCaller.getObject(contributor.getUrl(), User.class);
            this.contributors.add(
                    new ContributorDto(
                            user.getName(),
                            contributor.getLogin(),
                            contributor.getQtdCommits()
                    )
            );
        });
    }

    @Override
    public void setIssues(List<Issue> issues) {
        this.issues.clear();
        issues.forEach(issue -> {
            this.issues.add(
                    new IssueDto(
                            issue.getTitle(),
                            issue.getUser().getLogin(),
                            issue.getLabels().stream().map(Label::getName).collect(Collectors.toList())
                    )
            );
        });
    }

    public RepositoryDto repositoryDto(){
        return new RepositoryDto(user, repositoryName, issues, contributors);
    }
}
