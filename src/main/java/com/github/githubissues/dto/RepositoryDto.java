package com.github.githubissues.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({"user", "repository","issues", "contributors"})
public class RepositoryDto {

    private String user;

    @JsonProperty("repository")
    private String repositoryName;
    private List<IssueDto> issues;
    private List<ContributorDto> contributors;
}
