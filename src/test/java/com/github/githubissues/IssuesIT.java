package com.github.githubissues;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssuesIT {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    @Order(1)
    void scheduleSuccess() {
        //Arrange

        //Act
        final ResponseEntity<String> response =
                testRestTemplate.getForEntity("/issues/hauffe/golden-raspberry-awards", String.class);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    void userNotFound() {
        //Arrange

        //Act
        final ResponseEntity<String> response =
                testRestTemplate.getForEntity("/issues/strange_user_123/golden-raspberry-awards", String.class);

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    void dependabot() {
        //Arrange

        //Act
        final ResponseEntity<String> response =
                testRestTemplate.getForEntity("/issues/Hauffe/BusNews", String.class);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
