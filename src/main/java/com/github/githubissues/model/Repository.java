package com.github.githubissues.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Repository {
    private String user;
    private String repository;
    private List<Issue> issues;
    private List<Contributor> contributors;
}
