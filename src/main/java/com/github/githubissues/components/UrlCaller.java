package com.github.githubissues.components;

import com.github.githubissues.service.IssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UrlCaller {

    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public UrlCaller() {
        this.restTemplate = new RestTemplate();
    }


    public Object getObject(String url, Class className){
        logger.info("Calling - " + url);
        Object object = restTemplate.getForObject(url, className);
        return object;
    }

    public List<Object> getList(String url, Class className){
        logger.info("Calling - " + url);
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, className);
        List<Object> objectList = List.of(response.getBody());
        return objectList;
    }

}
