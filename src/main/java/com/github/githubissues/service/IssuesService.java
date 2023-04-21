package com.github.githubissues.service;

import com.github.githubissues.builders.RepositoryBuilder;
import com.github.githubissues.components.IssuesTasks;
import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.dto.ReturnMessageDto;
import com.github.githubissues.exceptions.RemoteItemNotFoundException;
import com.github.githubissues.model.Contributor;
import com.github.githubissues.model.Issue;
import com.github.githubissues.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IssuesService {

    @Value("${app.git_user}")
    private String gitUser;

    @Value("${app.git_repo}")
    private String gitRepo;

    private IssuesTasks tasks;

    private UrlCaller urlCaller;

    private RepositoryBuilder builder;

    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public IssuesService(UrlCaller urlCaller, IssuesTasks tasks, RepositoryBuilder builder) {
        this.tasks = tasks;
        this.urlCaller = urlCaller;
        this.builder = builder;

    }

    public void createResponse(RepositoryDto dto){
        try {
            tasks.pushIssuer(dto);
        }catch (InterruptedException ie){
            logger.error("Error on: " + ie);
        }
    }


    public RepositoryDto getRepositoryDto(String userId, String repositoryName){
        RepositoryDto dto;
        User user = getUser(userId);
        if(user != null){
            List<Issue> issues = getIssues(userId, repositoryName);
            List<Contributor> contributors = getContributors(userId, repositoryName);
            builder.setUser(user);
            builder.setRepository(repositoryName);
            builder.setIssues(issues);
            builder.setContributors(contributors);
            dto = builder.repositoryDto();
            createResponse(dto);
        }else{
            return null;
        }
        return dto;
    }

    public User getUser(String userId){
        String url = gitUser+userId;
        User user = null;
        try{
            user = (User)urlCaller.getObject(url, User.class);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return user;
    }

    public List<Issue> getIssues(String userId, String repository){
        List<Issue> issues = new ArrayList<>();
        String url = gitRepo+userId +
                "/" +
                repository +
                "/issues";
        try{
            Object obj = urlCaller.getList(url, Issue[].class);
            issues = (List<Issue>)obj;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return issues;
    }

    public List<Contributor> getContributors(String userId, String repository){
        List<Contributor> contributors = new ArrayList<>();
        String url = gitRepo+userId +
                "/" +
                repository +
                "/contributors";
        try {
            Object obj = urlCaller.getList(url, Contributor[].class);
            contributors = (List<Contributor>)obj;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return contributors;
    }
}
