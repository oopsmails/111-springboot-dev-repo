package com.oopsmails.springboot.mockbackend.githubuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupService {
    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async(value = "generalTaskExecutor")
    public CompletableFuture<GithubUser> findUserAsyncMock(String user) throws InterruptedException {
        GithubUser result = GithubUser.getMock(user);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(3000L);
        return CompletableFuture.completedFuture(result);
    }

    public GithubUser findUserMock(String name) throws InterruptedException {
        GithubUser result = GithubUser.getMock(name);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(3000L);
        //        logger.info("adding: [{}]", name);
        return result;
    }

    @Async(value = "generalTaskExecutor")
    public CompletableFuture<GithubUser> findUserAsync(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        GithubUser results = restTemplate.getForObject(url, GithubUser.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(3000L);
        return CompletableFuture.completedFuture(results);
    }

    public GithubUser findUser(String user) throws InterruptedException {
        String url = String.format("https://api.github.com/users/%s", user);
        GithubUser result = restTemplate.getForObject(url, GithubUser.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(2000L);
        return result;
    }
}
