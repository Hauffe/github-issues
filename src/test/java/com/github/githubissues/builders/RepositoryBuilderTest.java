package com.github.githubissues.builders;

import com.github.githubissues.components.UrlCaller;
import com.github.githubissues.controller.IssuesController;
import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.model.*;
import com.github.githubissues.service.IssuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryBuilderTest {

    @InjectMocks
    private RepositoryBuilder builder;

    @Mock
    private UrlCaller urlCaller;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buildRepositoryDtoSuccess() {
        //Arrange
        User user = prepareUser();
        List<Issue> issues = prepareIssues();
        List<Contributor> contributors = prepareContributor();
        Mockito.when(urlCaller.getObject("url", User.class))
                .thenReturn(prepareUser());

        //Act
        builder.setUser(prepareUser());
        builder.setContributors(prepareContributor());
        builder.setIssues(prepareIssues());
        RepositoryDto dto = builder.repositoryDto();

        //Assert
        assertNotNull(dto);
        assertEquals(user.getName(), dto.getUser());
        assertEquals(issues.get(0).getTitle(), dto.getIssues().get(0).getTitle());
        assertEquals(contributors.get(0).getQtdCommits(), dto.getContributors().get(0).getQtdCommits());
    }

    protected User prepareUser(){
        return new User("login", "name", "url");
    }

    protected List<Issue> prepareIssues(){
        List<Issue> issues = new ArrayList<>();
        List<Label> labels = new ArrayList<>();
        labels.add(new Label(1L, "bug"));
        issues.add(new Issue("title", prepareUser(), labels));
        return issues;
    }

    protected List<Contributor> prepareContributor(){
        List<Contributor> contributors = new ArrayList<>();
        contributors.add(new Contributor("login", "url", 10));
        return contributors;
    }
}