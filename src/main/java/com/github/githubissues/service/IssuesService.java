package com.github.githubissues.service;

import com.github.githubissues.components.IssuesTasks;
import com.github.githubissues.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssuesService {

    private IssuesTasks tasks;

    private static final Logger logger = LoggerFactory.getLogger(IssuesService.class);

    @Autowired
    public IssuesService(IssuesTasks tasks) {
        this.tasks = tasks;
    }


    public void findRepository(String user_id, String nome_repositorio){
        Repository repository = new Repository();
        try {
            tasks.pushIssuer(repository);
        }catch (InterruptedException ie){
            logger.error("Error on: " + ie);
        }
    }
}
