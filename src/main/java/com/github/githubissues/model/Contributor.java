package com.github.githubissues.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class Contributor {
    private String login;
    private String url;
    private Integer qtd_commits;

}
