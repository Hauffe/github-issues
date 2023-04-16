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
public class IssueDto {
//    title, author and labels
    private String title;
    private String author;
    private List<String> labels;
}
