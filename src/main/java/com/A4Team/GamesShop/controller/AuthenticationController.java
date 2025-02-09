package com.A4Team.GamesShop.controller;


import com.A4Team.GamesShop.model.response.AuthResponse;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.model.request.LoginRequest;
import com.A4Team.GamesShop.model.response.BaseResponse;
import com.A4Team.GamesShop.services.AuthenticationService;
import com.A4Team.GamesShop.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login-with-password")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        BaseResponse<AuthResponse> response = authenticationService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
