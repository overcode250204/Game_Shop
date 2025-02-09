package com.A4Team.GamesShop.utils;

import com.A4Team.GamesShop.exception.GoogleAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GoogleAuthenticationHelper {
    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.token-url}")
    private String tokenUrl;

    @Value("${google.user-info-url}")
    private String userInfoUrl;



    public String getGoogleAuthUrl() {
        return "https://accounts.google.com/o/oauth2/auth?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code&scope=email%20profile";
    }

    public Map<String, Object> getGoogleUserInfo(String authorizationCode) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String requestBody = "code=" + authorizationCode +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&redirect_uri=" + redirectUri +
                    "&grant_type=authorization_code";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new GoogleAuthenticationException("Failed to get access token", HttpStatus.UNAUTHORIZED);
            }


            String accessToken = (String) response.getBody().get("access_token");

            if (accessToken == null || accessToken.isEmpty()) {
                throw new GoogleAuthenticationException("Access token is missing", HttpStatus.UNAUTHORIZED);
            }
            HttpHeaders authHeaders = new HttpHeaders();
            authHeaders.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> authEntity = new HttpEntity<>(authHeaders);
            ResponseEntity<Map> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, authEntity, Map.class);
            if (!userResponse.getStatusCode().is2xxSuccessful()) {
                throw new GoogleAuthenticationException("Failed to fetch user info", HttpStatus.UNAUTHORIZED);
            }

            return userResponse.getBody();
        } catch (Exception e) {
            throw new GoogleAuthenticationException("Google Authentication Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
