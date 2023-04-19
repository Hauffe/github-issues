package com.github.githubissues.components;

import com.github.githubissues.model.*;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UrlCallerTest {

    @InjectMocks
    private UrlCaller urlCaller;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getObject() {
        //Assert
        ResponseEntity response = new ResponseEntity(prepareUser(), HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity("url", User.class))
                .thenReturn(response);

        //Act
        User user = (User) urlCaller.getObject("url", User.class);

        //Assert
        assertEquals(user, response.getBody());
    }

    @Test
    void getList() {
        //Assert
        List<Issue> expected = prepareIssues();
        ResponseEntity response = new ResponseEntity(expected.toArray(), HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity("url", Issue[].class))
                .thenReturn(response);

        //Act
        Object obj = urlCaller.getList("url", Issue[].class);
        List<Issue> issues = (List<Issue>)obj;

        //Assert
        assertEquals(expected, issues);
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
}