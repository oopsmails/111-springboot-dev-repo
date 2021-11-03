package com.oopsmails.springboot.mockbackend.githubuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class GitHubLookupServiceRunner {
    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupServiceRunner.class);

    @Autowired
    private GitHubLookupService gitHubLookupService;

    private final RestTemplate restTemplate;

    public GitHubLookupServiceRunner(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

//    @Async(value = "generalTaskExecutor")
//    public CompletableFuture<List<GithubUser>> findUserAsync(List<String> userNames) throws InterruptedException {
//        logger.info("Looking up: " + userNames);
//        List<GithubUser> result = new ArrayList<>();
//
//        userNames.forEach((userName) -> {
//            String url = String.format("https://api.github.com/users/%s", userName);
//            GithubUser gitHubUser = restTemplate.getForObject(url, GithubUser.class);
//            result.add(gitHubUser);
//            // Artificial delay of 1s for demonstration purposes
//            Thread.sleep(1000L);
//        });
//
//        userNames.forEach(throwingConsumerWrapper(userName -> {
//            CompletableFuture<GithubUser> githubUserFuture = gitHubLookupService.findUserAsync(userName);
//            githubUserFuture.thenApply();
//        }));
//
//        return CompletableFuture.completedFuture(result);
//    }

    public List<GithubUser> findAllUser(List<String> userNames) throws Exception {
        List<CompletableFuture<GithubUser>> completableFutures = userNames.stream()
                .map(userName -> {
                    try {
                        return gitHubLookupService.findUserAsyncMock(userName);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
                completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

        CompletableFuture<List<GithubUser>> allCompletableFuture = combinedFuture.thenApply(future -> {
            return completableFutures.stream()
                    .map(completableFuture -> completableFuture.join())
                    .collect(Collectors.toList());
        });
        List<GithubUser> result = allCompletableFuture.get(5, TimeUnit.MINUTES);
        return result;
    }

    @Deprecated
    public List<GithubUser> findAllUserRefOnly(List<String> userNames) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

//        // Kick of multiple, asynchronous lookups
//        CompletableFuture<GithubUser> future1 = gitHubLookupService.findUserAsync("PivotalSoftware");
//        CompletableFuture<GithubUser> future2 = gitHubLookupService.findUserAsync("CloudFoundry");
//        CompletableFuture<GithubUser> future3 = gitHubLookupService.findUserAsync("Spring-Projects");
//
//        // Wait until they are all done
//        List<GithubUser> result = Stream.of(future1, future2, future3).map(CompletableFuture::join).collect(Collectors.toList());
//
//        // Print results, including elapsed time
//        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
//        logger.info("--> " + future1.get());
//        logger.info("--> " + future2.get());
//        logger.info("--> " + future3.get());
//
//        return result;

        List<GithubUser> result = new ArrayList<>();

        // Async but not parallel
//        userNames.forEach(throwingConsumerWrapper(userName -> {
//            CompletableFuture<GithubUser> githubUserFuture = gitHubLookupService.findUserAsync(userName);
//            githubUserFuture.thenApply(githubUser -> result.add(githubUser));
//        }));

        // parallel
//        userNames.parallelStream().forEach(throwingConsumerWrapper(userName -> {
//            CompletableFuture<GithubUser> githubUserFuture = gitHubLookupService.findUserAsync(userName);
//            githubUserFuture.thenApply(githubUser -> result.add(githubUser));
//        }));



        return result;
    }
}
