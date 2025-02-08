package com.A4Team.GamesShop.controller;


import com.A4Team.GamesShop.services.AuthenticationService;
import com.A4Team.GamesShop.utils.JwtHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/google")
@Tag(name = "Google Authentication Controller")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping("/google-url")
    public ResponseEntity<String> getGoogleAuthUrl() {
        return ResponseEntity.ok(authenticationService.googleAuthUrl());
    }

    @PostMapping("/google-login")
    public ResponseEntity<String> googleLogin(@RequestParam String authozationCode) {
        String token = authenticationService.authenWithGoogle(authozationCode);
        return ResponseEntity.ok(token);
    }


    @GetMapping("/testToken")
    public ResponseEntity<?> testToken(String token) {
        return new ResponseEntity<>(jwtHelper.getDataToken(token), HttpStatus.OK);
    }













}
