package com.A4Team.GamesShop.controller;


import com.A4Team.GamesShop.model.response.AuthResponse;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.model.request.LoginRequest;
import com.A4Team.GamesShop.model.response.BaseResponse;
import com.A4Team.GamesShop.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login-with-password")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        BaseResponse<AuthResponse> response = authenticationService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponse<UserAuthResponse>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserAuthResponse)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.error(
                            HttpStatus.UNAUTHORIZED,
                            "User not authenticated",
                            List.of("Invalid token or session expired")
                    ));
        }
        UserAuthResponse user = (UserAuthResponse) authentication.getPrincipal();
        return ResponseEntity.ok(BaseResponse.success(user, "User retrieved successfully"));
    }


}
