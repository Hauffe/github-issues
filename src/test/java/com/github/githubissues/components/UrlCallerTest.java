package com.github.githubissues.components;

import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UrlCallerTest {

    @InjectMocks
    private UrlCaller urlCaller;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MultiValueMap<String, String> headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getObjectSuccess() {
        //Arrange
        ResponseEntity expected = new ResponseEntity(prepareUser(), HttpStatus.OK);
        Mockito.when(restTemplate.exchange("url", HttpMethod.GET, new HttpEntity<>(headers), User.class))
                .thenReturn(expected);

        //Act
        User user = (User) urlCaller.getObject("url", User.class);

        //Assert
        assertEquals(expected.getBody(), user);
    }

    @Test
    void getObjectNotFound() {
        //Arrange
        Object returned;
        ResponseEntity expected = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.exchange("wrong_url", HttpMethod.GET, new HttpEntity<>(headers), User.class))
                .thenReturn(expected);

        //Act
        returned = urlCaller.getObject("wrong_url", User.class);

        //Assert
        assertNull(returned);
    }

    @Test
    void getListSuccess() {
        //Arrange
        List<Issue> expected = prepareIssues();
        ResponseEntity response = new ResponseEntity(expected.toArray(), HttpStatus.OK);
        Mockito.when(restTemplate.exchange("url", HttpMethod.GET, new HttpEntity<>(headers), Issue[].class))
                .thenReturn(response);

        //Act
        Object obj = urlCaller.getList("url", Issue[].class);
        List<Issue> issues = (List<Issue>)obj;

        //Assert
        assertEquals(expected, issues);
    }

    @Test
    void getListNotFound() {
        //Arrange
        List<Object> objects;
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.exchange("wrong_url", HttpMethod.GET, new HttpEntity<>(headers), Issue[].class))
                .thenReturn(response);

        //Act
        objects = urlCaller.getList("wrong_url", Issue[].class);

        //Assert
        assertNull(objects);

    }


    @Test
    void postSuccess() {
        //Arrange
        RepositoryDto dto = new RepositoryDto();
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity("url", dto, String.class))
                .thenReturn(response);

        //Act
        urlCaller.post("url", dto);
    }

    @Test
    void postFail() {
        //Arrange
        RepositoryDto dto = new RepositoryDto();
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.postForEntity("url", dto, String.class))
                .thenReturn(response);

        //Act
        urlCaller.post("url", dto);
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
}