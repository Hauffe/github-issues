package com.github.githubissues.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
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
