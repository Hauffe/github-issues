package com.github.githubissues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class GithubIssuesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubIssuesApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // max numbers of requests
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        // Queue capacity
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Webhoook-");
        executor.initialize();
        return executor;
    }

}
