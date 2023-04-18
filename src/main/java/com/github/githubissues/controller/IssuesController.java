package com.github.githubissues.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.githubissues.service.IssuesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/issues")
public class IssuesController {

    private final IssuesService service;

    private ObjectMapper mapper;

    public IssuesController(IssuesService service) {
        this.service = service;
        this.mapper = new ObjectMapper();
    }

    @RequestMapping("/about")
    public ResponseEntity<String> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("Github information API (Version 1.0) created by Alexandre Hauffe");
    }

    @GetMapping("/{user_id}/{repository}")
    public ResponseEntity getRepository(
            @PathVariable String user_id,
            @PathVariable String repository){
        ResponseEntity response = service.getRepository(user_id, repository);
        return response;
    }
}
