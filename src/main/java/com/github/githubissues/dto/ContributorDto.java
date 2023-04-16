package com.github.githubissues.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class ContributorDto {
    private String nome;
    private String user;
    private Integer qtd_commits;
}
