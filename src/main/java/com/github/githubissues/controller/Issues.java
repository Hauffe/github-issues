package com.github.githubissues.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/issues")
public class Issues {

    @RequestMapping("/about")
    public ResponseEntity<String> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("Github information API (Version 1.0) created by Alexandre Hauffe");
    }
}
