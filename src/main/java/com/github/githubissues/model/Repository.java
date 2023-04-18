package com.github.githubissues.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Repository {
    @JsonAlias("user_id")
    private String user;
    private String repository;
    private List<Issue> issues;
    private List<Contributor> contributors;

}
