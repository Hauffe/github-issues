package com.github.githubissues.builders;

import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.dto.ContributorDto;
import com.github.githubissues.dto.IssueDto;
import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.model.Contributor;
import com.github.githubissues.model.Issue;
import com.github.githubissues.model.Label;
import com.github.githubissues.model.User;
import com.github.githubissues.service.IssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

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

    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public RepositoryBuilder(UrlCaller urlCaller) {
        this.urlCaller = urlCaller;
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
        this.contributors = new ArrayList<>();
        contributors.forEach(contributor -> {
            User user = new User();
            try {
                user = (User) urlCaller.getObject(contributor.getUrl(), User.class);
            }catch (HttpClientErrorException e){
                logger.info(e.getMessage());
                user.setName("Not found");
            }
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
        this.issues = new ArrayList<>();
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
