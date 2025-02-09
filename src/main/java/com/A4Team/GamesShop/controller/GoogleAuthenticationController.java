package com.A4Team.GamesShop.controller;



import com.A4Team.GamesShop.model.response.BaseResponse;
import com.A4Team.GamesShop.services.GoogleAuthenticationService;
import com.A4Team.GamesShop.utils.JwtHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/google")
@Tag(name = "Google Authentication Controller")
public class GoogleAuthenticationController {
    @Autowired
    private GoogleAuthenticationService authenticationService;

    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping("/google-url")
    public ResponseEntity<?> getGoogleAuthUrl() {
        BaseResponse<?> response = authenticationService.googleAuthUrl();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestParam String authozationCode) {
        BaseResponse<?> response = authenticationService.generateToken(authozationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
















}
