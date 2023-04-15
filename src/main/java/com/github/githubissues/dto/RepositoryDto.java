package com.github.githubissues.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.githubissues.model.Contributor;
import com.github.githubissues.model.Issue;
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
public class RepositoryDto {

    private String user;
    private String repository;
    private List<IssueDto> issues;
    private List<ContributorDto> contributors;
}
