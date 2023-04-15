package com.github.githubissues.components;

import com.github.githubissues.dto.RepositoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class IssuesTasks {


    @Value("${app.webhook}")
    private String webhookUrl;

    private static final Logger logger = LoggerFactory.getLogger(IssuesTasks.class);

    @Async
    public void pushIssuer(RepositoryDto repository) throws InterruptedException {
        logger.info("run");
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(5000L);
        logger.info("finish");
    }

}
