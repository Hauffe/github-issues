package com.github.githubissues.service;

import com.github.githubissues.builders.RepositoryBuilder;
import com.github.githubissues.components.IssuesTasks;
import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.controller.IssuesController;
import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.exceptions.RemoteItemNotFoundException;
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
        ReflectionTestUtils.setField(issuesService, "builder", new RepositoryBuilder(urlCaller));
        User user = prepareUser();
        List<Issue> issues = prepareIssues();
        List<Contributor> contributors = prepareContributor();
        Mockito.when(urlCaller.getObject("url", User.class))
                .thenReturn(user);
        Mockito.when(issuesService.getUser("user"))
                .thenReturn(user);
        Mockito.when(issuesService.getIssues("user", "repo"))
                .thenReturn(issues);
        Mockito.when(issuesService.getContributors("user", "repo"))
                .thenReturn(contributors);

        //Act
        RepositoryDto response = issuesService.getRepositoryDto("user", "repo");

        //Assert
        assertNotNull(response);
        assertEquals("name", response.getUser());
        assertEquals(issues.get(0).getTitle(), response.getIssues().get(0).getTitle());
        assertEquals(contributors.get(0).getQtdCommits(), response.getContributors().get(0).getQtdCommits());
    }

    @Test
    void getUserSuccess() {
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
    void getUserNotFound() {
        //Arrange
        Mockito.when(urlCaller.getObject("user.api.url/"+"user", User.class))
                .thenThrow(new RemoteItemNotFoundException());

        //Act
        User user = issuesService.getUser("user");

        //Assert
        assertNull(user);
    }


    @Test
    void getIssuesSuccess() {
        //Arrange
        String url = "repo.api.url/user/repository/issues";
        List<Issue> issues = new ArrayList<>();
        List<Object> objects = new ArrayList<>(issues);
        Mockito.when(urlCaller.getList(url, Issue[].class)).thenReturn(objects);

        //Act
        var response = issuesService.getIssues("user", "repository");

        //Assert
        assertNotNull(response);
        assertEquals(response, issues);
    }

    @Test
    void getContributorsSuccess() {
        //Arrange
        String url = "repo.api.url/user/repository/issues";
        List<Contributor> contributors = new ArrayList<>();
        List<Object> objects = new ArrayList<>(contributors);
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