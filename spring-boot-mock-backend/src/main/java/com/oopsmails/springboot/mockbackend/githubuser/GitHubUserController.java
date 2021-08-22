package com.oopsmails.springboot.mockbackend.githubuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/backendmock/githubuser-api")
@Slf4j
public class GitHubUserController {

    @Autowired
    private GitHubLookupService gitHubLookupService;

    @Autowired
    private GitHubLookupServiceRunner gitHubLookupServiceRunner;

    @GetMapping("")
    public List<GithubUser> findAll() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<GithubUser> future1 = gitHubLookupService.findUserAsync("PivotalSoftware");
        CompletableFuture<GithubUser> future2 = gitHubLookupService.findUserAsync("CloudFoundry");
        CompletableFuture<GithubUser> future3 = gitHubLookupService.findUserAsync("Spring-Projects");

        // Wait until they are all done

        /**
         * String combined = Stream.of(future1, future2, future3)
         *   .map(CompletableFuture::join)
         *   .collect(Collectors.joining(" "));
         *
         * assertEquals("Hello Beautiful World", combined);
         */

        // original
//        CompletableFuture.allOf(page1,page2,page3).join();

        List<GithubUser> result = Stream.of(future1, future2, future3).map(CompletableFuture::join).collect(Collectors.toList());

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + future1.get());
        log.info("--> " + future2.get());
        log.info("--> " + future3.get());

        return result;
    }

    @GetMapping("/all")
    public List<GithubUser> findAllUsers() throws Exception {
        List<String> githubUserNames =  new ArrayList<>();
        githubUserNames.add("PivotalSoftware");
        githubUserNames.add("CloudFoundry");
        githubUserNames.add("Spring-Projects");

        return gitHubLookupServiceRunner.findAllUser(githubUserNames);
    }
}
