package com.sritel.billingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean getAuthorizationFromAccountService(String token) {
        String url = "http://ACCOUNT-SERVICE/auth/getUser";

        HttpHeaders headers = new HttpHeaders();
        headers.set("jwtToken", token);
        System.out.println("Token: " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
