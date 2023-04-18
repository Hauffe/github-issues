package com.github.githubissues.components;

import com.github.githubissues.dto.RepositoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class IssuesTasks {


    @Value("${app.webhook}")
    private String webhookUrl;

    @Value("${app.timeToPush}")
    private Long timeToPush;

    private UrlCaller urlCaller;

    private static final Logger logger = LoggerFactory.getLogger(IssuesTasks.class);

    @Autowired
    public IssuesTasks(UrlCaller urlCaller) {
        this.urlCaller = urlCaller;
    }

    @Async
    public void pushIssuer(RepositoryDto repository) throws InterruptedException {
        logger.info("Push to remote scheduled");
        Thread.sleep(timeToPush);
        urlCaller.post(webhookUrl, repository);
    }

}
