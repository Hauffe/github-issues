package com.github.githubissues.service;

import com.github.githubissues.builders.RepositoryBuilder;
import com.github.githubissues.components.IssuesTasks;
import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.controller.IssuesController;
import com.github.githubissues.model.*;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IssuesServiceTest {

    @InjectMocks
    private IssuesService issuesService;

    @Mock
    private UrlCaller urlCaller;

    @Mock
    private IssuesTasks tasks;

    @Mock
    private RepositoryBuilder builder;

    @BeforeEach
    void setUpEach() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(issuesService, "gitUser", "user.api.url/");
        ReflectionTestUtils.setField(issuesService, "gitRepo", "repo.api.url/");
    }

    @Test
    void getRepositorySuccess() {
        //Arrange
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        List<Issue> issues = prepareIssues();
        List<Contributor> contributors = prepareContributor();
        Mockito.when(issuesService.getUser("user"))
                .thenReturn(prepareUser());
        Mockito.when(issuesService.getIssues("user", "repository"))
                .thenReturn(issues);
        Mockito.when(issuesService.getContributors("user", "repository"))
                .thenReturn(contributors);

        //Act
        var responseEntity = issuesService.getRepository("user", "repo");

        //Assert
        assertNotNull(response);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), responseEntity.getStatusCode());
    }

    @Test
    void getUser() {
        //Arrange
        User user = prepareUser();
        Mockito.when(urlCaller.getObject("user.api.url/"+"user", User.class)).thenReturn(user);

        //Act
        var response = issuesService.getUser("user");

        //Assert
        assertNotNull(response);
        assertEquals(response, user);
    }

    @Test
    void getIssues() {
        //Arrange
        String url = "repo.api.url/user/repository/issues";
        List<Object> objects = new ArrayList<>();
        List<Issue> issues = new ArrayList<>();
        objects.addAll(issues);
        Mockito.when(urlCaller.getList(url, Issue[].class)).thenReturn(objects);

        //Act
        var response = issuesService.getIssues("user", "repository");

        //Assert
        assertNotNull(response);
        assertEquals(response, issues);
    }

    @Test
    void getContributors() {
        //Arrange
        String url = "repo.api.url/user/repository/issues";
        List<Object> objects = new ArrayList<>();
        List<Contributor> contributors = new ArrayList<>();
        objects.addAll(contributors);
        Mockito.when(urlCaller.getList(url, Contributor[].class)).thenReturn(objects);

        //Act
        var response = issuesService.getContributors("user", "repository");

        //Assert
        assertNotNull(response);
        assertEquals(response, contributors);
    }


    protected User prepareUser(){
        return new User("login", "name", "url");
    }

    protected List<Issue> prepareIssues(){
        List<Issue> issues = new ArrayList<>();
        List<Label> labels = new ArrayList<>();
        labels.add(new Label(1, "bug"));
        issues.add(new Issue("title", prepareUser(), labels));
        return issues;
    }

    protected List<Contributor> prepareContributor(){
        List<Contributor> contributors = new ArrayList<>();
        contributors.add(new Contributor("login", "url", 10));
        return contributors;
    }

    protected Repository prepateRepository(){
        return new Repository("user", "repo", prepareIssues(), prepareContributor());
    }
}