package com.github.githubissues.service;

import com.github.githubissues.builders.RepositoryBuilder;
import com.github.githubissues.components.IssuesTasks;
import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.dto.RepositoryDto;
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


    // TODO: change return
    public ResponseEntity getRepository(String user_id, String nome_repositorio){
        User user = getUser(user_id);
        List<Issue> issues = getIssues(user_id, nome_repositorio);
        List<Contributor> contributors = getContributors(user_id, nome_repositorio);
        builder.setUser(user);
        builder.setRepository(nome_repositorio);
        builder.setIssues(issues);
        builder.setContributors(contributors);
        createResponse(builder.repositoryDto());
        return new ResponseEntity(HttpStatus.OK);
    }

    public User getUser(String user_id){
        String url = gitUser+user_id;;
        User user = (User)urlCaller.getObject(url, User.class);
        return user;
    }

    public List<Issue> getIssues(String user_id, String repository){
        String url = gitRepo+user_id +
                "/" +
                repository +
                "/issues";
        Object obj = urlCaller.getList(url, Issue[].class);
        return (List<Issue>)obj;
    }

    public List<Contributor> getContributors(String user_id, String repository){
        String url = gitRepo+user_id +
                "/" +
                repository +
                "/contributors";
        List<Contributor> contributorList = (List<Contributor>)(Object)urlCaller.getList(url, Contributor[].class);
        return contributorList;
    }
}
