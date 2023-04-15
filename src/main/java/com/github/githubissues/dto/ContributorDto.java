package com.github.githubissues.dto;


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
public class ContributorDto {
    //name, user, qtd_commits
}
