package com.github.githubissues.controller;

import com.github.githubissues.service.IssuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

class IssuesControllerTest {

    @InjectMocks
    private IssuesController issuesController;

    @Mock
    private IssuesService issuesService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRepositorySuccess() {
        //Arrange
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        Mockito.when(issuesService.getRepository("user", "repo"))
                .thenReturn(response);

        //Act
        var responseEntity = issuesController.getRepository("user", "repo");
        var result = responseEntity.getBody();

        //Assert
        assertEquals(ResponseEntity.ok().build().getStatusCode(), responseEntity.getStatusCode());
        assertNotNull(response);
    }

    @Test
    void getRepositoryError() {
        //Arrange
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        Mockito.when(issuesService.getRepository("user", "repo"))
                .thenReturn(response);

        //Act
        var responseEntity = issuesController.getRepository("user", "repo");
        var result = responseEntity.getBody();

        //Assert
        assertEquals(ResponseEntity.badRequest().build().getStatusCode(), responseEntity.getStatusCode());
        assertNotNull(response);
    }
}