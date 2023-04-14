package com.github.githubissues.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Issue {
    private String title;
    private String author;
    private List<String> labels;
}
