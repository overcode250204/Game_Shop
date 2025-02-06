package com.A4Team.GamesShop.controller;


import com.A4Team.GamesShop.dto.AuthDTO;
import com.A4Team.GamesShop.request.LoginRequest;
import com.A4Team.GamesShop.response.BaseResponse;
import com.A4Team.GamesShop.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login-with-password")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        BaseResponse<AuthDTO> response = authenticationService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
