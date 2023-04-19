package com.github.githubissues.components;

import com.github.githubissues.service.IssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        ResponseEntity response = restTemplate.getForEntity(url, className);
        return response.getBody();
    }

    public List<Object> getList(String url, Class className){
        logger.info("Calling - " + url);
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, className);
        List<Object> objectList = List.of(response.getBody());
        return objectList;
    }

    public void post(String url, Object object){
        logger.info("POST to - " + url);
        ResponseEntity response = restTemplate.postForEntity(url, object, String.class);
        if(response.getStatusCode() != HttpStatus.OK){
            logger.error("Unable to POST - " + response);
        }else{
            logger.info("Request sent to " + url);
        }
    }
}
