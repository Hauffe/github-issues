package com.github.githubissues.controller;

import com.github.githubissues.dto.RepositoryDto;
import com.github.githubissues.dto.ReturnMessageDto;
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
        RepositoryDto dto = new RepositoryDto();
        Mockito.when(issuesService.getRepositoryDto("user", "repo"))
                .thenReturn(dto);

        //Act
        var response = issuesController.getRepository("user", "repo");

        //Assert
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void getRepositoryErrorNotFound() {
        //Arrange
        Mockito.when(issuesService.getRepositoryDto("user", "repo"))
                .thenReturn(null);

        //Act
        var response = issuesController.getRepository("user", "repo");

        //Assert
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
    }
}