package com.github.githubissues.components;

import com.github.githubissues.service.IssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UrlCaller {

    @Value("${app.git_token}")
    private String gitToken;

    private RestTemplate restTemplate;

    private  MultiValueMap<String, String> headers;


    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public UrlCaller() {
        this.restTemplate = new RestTemplate();
        this.headers = new LinkedMultiValueMap<>();
    }


    public Object getObject(String url, Class className){
        headers.clear();
        headers = setHeaders();
        Object object;
        logger.info("Calling - " + url);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), className);
        object = response.getBody();
        return object;
    }

    public List<Object> getList(String url, Class className){
        headers.clear();
        headers = setHeaders();
        List<Object> objectList = null;
        logger.info("Calling - " + url);
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), className);
        if(response.getBody() != null)
            objectList = List.of(response.getBody());
        return objectList;
    }

    public void post(String url, Object object){
        logger.info("POST to - " + url);
        ResponseEntity response = restTemplate.postForEntity(url, object, String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Success POST to: " + url);
        }else{
            logger.error("Fail to POST to: " + url + " returned " + response);
        }
    }

    private MultiValueMap<String, String> setHeaders(){
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "token "+gitToken);
        return headers;
    }
}
