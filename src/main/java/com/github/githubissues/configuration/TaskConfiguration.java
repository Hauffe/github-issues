package com.github.githubissues.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class TaskConfiguration {

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
