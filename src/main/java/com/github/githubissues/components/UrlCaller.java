package com.github.githubissues.components;

import com.github.githubissues.exceptions.RemoteItemNotFoundException;
import com.github.githubissues.service.IssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class UrlCaller {

    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public UrlCaller() {
        this.restTemplate = new RestTemplate();
    }


    public Object getObject(String url, Class className) throws RemoteItemNotFoundException{
        Object object = new Object();
        logger.info("Calling - " + url);
        ResponseEntity response = restTemplate.getForEntity(url, className);
        if(validateReturn(response))
            object = response.getBody();
        return object;
    }

    public List<Object> getList(String url, Class className) throws RemoteItemNotFoundException{
        List<Object> objectList = new ArrayList<>();
        logger.info("Calling - " + url);
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, className);
        if(validateReturn(response))
            objectList = List.of(response.getBody());
        return objectList;
    }

    public void post(String url, Object object) throws RemoteItemNotFoundException{
        logger.info("POST to - " + url);
        ResponseEntity response = restTemplate.postForEntity(url, object, String.class);
        if(validateReturn(response))
            logger.info("Success POST to: " + url);
    }

    public boolean validateReturn(ResponseEntity response) throws RemoteItemNotFoundException{
        if(HttpStatus.NOT_FOUND.equals(response.getStatusCode())){
            throw new RemoteItemNotFoundException("No item found from remote API: " + response);
        }
        if(!HttpStatus.OK.equals(response.getStatusCode())){
            throw new RuntimeException("Unable to POST or GET values: " + response);
        }
        return true;
    }
}
