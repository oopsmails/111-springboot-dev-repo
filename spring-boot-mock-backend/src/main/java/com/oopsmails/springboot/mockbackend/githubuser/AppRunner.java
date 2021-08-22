package com.oopsmails.springboot.mockbackend.githubuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * This is just for testing purpose, running on startup
 */

@Component
public class AppRunner { //implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    //    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<GithubUser> page1 = gitHubLookupService.findUserAsync("PivotalSoftware");
        CompletableFuture<GithubUser> page2 = gitHubLookupService.findUserAsync("CloudFoundry");
        CompletableFuture<GithubUser> page3 = gitHubLookupService.findUserAsync("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
    }

}
