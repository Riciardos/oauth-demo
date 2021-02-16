package com.matchesfashion.oauthdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubClient
{

    @Value("${github.client.id}")
    String githubClientId;

    public String getAuthorizationCode() {
        RestTemplate template = new RestTemplate();
        return template.getForEntity("https://github.com/login/oauth/authorize?client_id=" + githubClientId, String.class).getBody();
    }
}
