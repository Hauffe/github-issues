package com.github.githubissues.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Contributor {
    private String name;
    private String user;
    private Integer qtd_commits;

}
