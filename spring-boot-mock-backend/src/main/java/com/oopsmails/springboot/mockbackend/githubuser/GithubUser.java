package com.oopsmails.springboot.mockbackend.githubuser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class GithubUser {
    private String name;
    private String blog;

    public static GithubUser getMock() {
        return getMock(null);
    }

    public static GithubUser getMock(String name) {
        GithubUser result = new GithubUser();
        result.setName(name == null ? "" + System.currentTimeMillis() : name);
        result.setBlog("" + System.currentTimeMillis());

        return result;
    }
}
