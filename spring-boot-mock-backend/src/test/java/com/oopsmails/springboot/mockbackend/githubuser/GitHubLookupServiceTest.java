package com.oopsmails.springboot.mockbackend.githubuser;

import com.oopsmails.springboot.mockbackend.SpringBootBackendMockApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.oopsmails.springboot.mockbackend.consumer.ThrowingConsumer.throwingConsumerWrapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {SpringBootBackendMockApplication.class})
public class GitHubLookupServiceTest {
    public static final int NUM = 10;

    @Autowired
    private GitHubLookupService gitHubLookupService;

    @Test
    public void testFindAll_correct_01() throws Exception {
        List<String> githubUserNames = mockGithubUserNames(NUM);

        List<GithubUser> result = Collections.synchronizedList(new ArrayList<>());

        githubUserNames.parallelStream().forEach(throwingConsumerWrapper(userName -> {
            GithubUser githubUser = gitHubLookupService.findUserMock(userName);
            result.add(githubUser);
        }));

        assertNotNull(result, "result should not be null.");
        assertEquals(NUM, result.size(), "verifying size.");
    }

    @Test
    public void testFindAll_correct_02() throws Exception {
        List<String> githubUserNames = mockGithubUserNames(NUM);

        List<GithubUser> result = githubUserNames.parallelStream() //
                .filter(userName -> userName != null) //
                .map(userName -> { //
                    try {
                        return gitHubLookupService.findUserMock(userName);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        assertNotNull(result, "result should not be null.");
        assertEquals(NUM, result.size(), "verifying size.");
    }

    @Test
    public void testFindAll_wrong_01() throws Exception {
        List<String> githubUserNames = mockGithubUserNames(NUM);

        List<GithubUser> result = new ArrayList<>();

        githubUserNames.parallelStream().forEach(throwingConsumerWrapper(userName -> {
            GithubUser githubUser = gitHubLookupService.findUserMock(userName);
            result.add(githubUser);
        }));

        assertNotNull(result, "result should not be null.");
//        assertEquals(NUM, result.size(), "verifying size."); // will usually fail but saved by System.out
    }

    @Test
    public void testFindAll_wrong_02_savedByPrint() throws Exception {
        List<String> githubUserNames = mockGithubUserNames(NUM);

        List<GithubUser> result = new ArrayList<>();

        githubUserNames.parallelStream().forEach(throwingConsumerWrapper(userName -> {
            GithubUser githubUser = gitHubLookupService.findUserMock(userName);
            System.out.println(githubUser);
            result.add(githubUser);
        }));

        assertNotNull(result, "result should not be null.");
        assertEquals(NUM, result.size(), "verifying size."); // will usually fail but saved by System.out
    }


    @Test
    public void testFindAllAsync_correct_01() throws Exception {
        List<String> githubUserNames = new ArrayList<>();

        for (int i = 0; i < NUM; i++) {
            githubUserNames.add("PivotalSoftware-" + i);
//            githubUserNames.add("CloudFoundry");
//            githubUserNames.add("Spring-Projects");
        }


//        List<CompletableFuture<GithubUser>> futureList = new ArrayList<>();
//
//        githubUserNames.parallelStream().forEach(throwingConsumerWrapper(userName -> {
//            CompletableFuture<GithubUser> githubUserFuture = gitHubLookupService.findUserAsyncMock(userName);
//            futureList.add(githubUserFuture);
//        }));

        List<CompletableFuture<GithubUser>> completableFutures = githubUserNames.stream()
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

//        CompletableFuture completableFuture = allCompletableFuture.thenApply(greets -> {
//            return greets.stream().map(GreetHolder::getGreet).collect(Collectors.toList());
//        });

        List<GithubUser> result = allCompletableFuture.get(5, TimeUnit.MINUTES);

//        List<GithubUser> result = Collections.synchronizedList(new ArrayList<>());

        assertNotNull(result, "result should not be null.");
        assertEquals(NUM, result.size(), "verifying size.");
    }

    private List<String> mockGithubUserNames(int num) {
        List<String> mockGithubUserNames = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            mockGithubUserNames.add("num-" + i);
        }
        return mockGithubUserNames;
    }
}
