package com.oopsmails.springboot.mockbackend.githubuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.oopsmails.springboot.mockbackend.SpringBootBackendMockApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
        SpringBootBackendMockApplication.class
})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GitHubUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllGithubUserAPI() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/backendmock/githubuser-api/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<GithubUser> result = objectMapper.readValue(content, typeFactory.constructCollectionType(List.class, GithubUser.class));

        assertTrue(result.size() > 0);
    }

    //    @Test
    //    public void testFindAll() throws Exception {
    //        List<String> githubUserNames =  new ArrayList<>();
    //
    //        for (int i = 0; i < 100; i++) {
    //            githubUserNames.add("PivotalSoftware");
    //            githubUserNames.add("CloudFoundry");
    //            githubUserNames.add("Spring-Projects");
    //        }
    //
    //        List<GithubUser> result = new ArrayList<>();
    //        githubUserNames.parallelStream().forEach(throwingConsumerWrapper(userName -> {
    //            CompletableFuture<GithubUser> githubUserFuture = gitHubLookupService.findUserAsync(userName);
    //            githubUserFuture.thenApply(githubUser -> result.add(githubUser));
    //        }));
    //
    //        assertNotNull(result, "result should not be null.");
    //        assertEquals(300, result.size(), "verifying size.");
    //    }
}
