package com.github.githubissues.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class Repository {
    @JsonAlias("user_id")
    private String user;
    private String repository;
    private List<Issue> issues;
    private List<Contributor> contributors;

}
