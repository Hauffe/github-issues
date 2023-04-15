package com.github.githubissues.service;

import com.github.githubissues.components.IssuesTasks;
import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.model.Contributor;
import com.github.githubissues.model.Issue;
import com.github.githubissues.model.Repository;
import com.github.githubissues.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class IssuesService {


    @Value("${app.git_user}")
    private String gitUser;

    @Value("${app.git_repo}")
    private String gitRepo;

    private IssuesTasks tasks;

    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public IssuesService(IssuesTasks tasks) {
        this.tasks = tasks;
        this.restTemplate = new RestTemplate();
    }

    public void createResponse(){
        RepositoryDto dto = new RepositoryDto();
        try {
            tasks.pushIssuer(dto);
        }catch (InterruptedException ie){
            logger.error("Error on: " + ie);
        }
    }


    // TODO: change return
    public Repository findRepository(String user_id, String nome_repositorio){
        Repository repository = new Repository();
        User user = findUser(user_id);
        repository.setUser(user.getName());

        List<Issue> issues = findIssues(user_id, nome_repositorio);
        repository.setIssues(issues);
        repository.setRepository(nome_repositorio);

        List<Contributor> contributors = findContributors(user_id, nome_repositorio);
        repository.setContributors(contributors);

        return repository;
    }

    //TODO: validate null return
    public User findUser(String user_id){
        String url = gitUser+user_id;
        logger.info("Calling - " + url);
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }

    //TODO: validate null return
    public List<Issue> findIssues(String user_id, String repository){
        String url = gitRepo+user_id +
                "/" +
                repository +
                "/issues";
        logger.info("Calling - " + url);
        ResponseEntity<Issue[]> response = restTemplate.getForEntity(url, Issue[].class);
        List<Issue> issuesList = List.of(response.getBody());
        return issuesList;
    }

    public List<Contributor> findContributors(String user_id, String repository){
        String url = gitRepo+user_id +
                "/" +
                repository +
                "/contributors";
        logger.info("Calling - " + url);
        ResponseEntity<Contributor[]> response = restTemplate.getForEntity(url, Contributor[].class);
        List<Contributor> contributorList = List.of(response.getBody());
        return contributorList;
    }



}
