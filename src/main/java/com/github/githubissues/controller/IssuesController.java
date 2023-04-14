package com.github.githubissues.controller;

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

    public IssuesController(IssuesService service) {
        this.service = service;
    }

    @RequestMapping("/about")
    public ResponseEntity<String> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("Github information API (Version 1.0) created by Alexandre Hauffe");
    }

    @GetMapping("/repository/{user_id}/{repository}")
    public ResponseEntity getRepository(
            @PathVariable String user_id,
            @PathVariable String repository) {
        service.findRepository(user_id, repository);
        return new ResponseEntity<>(user_id + " " + repository, HttpStatus.OK);
    }
}
