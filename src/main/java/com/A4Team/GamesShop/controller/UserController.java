package com.A4Team.GamesShop.controller;

import com.A4Team.GamesShop.model.response.BaseResponse;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
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
        user = userService.processUserAvatar(user);
        return ResponseEntity.ok(BaseResponse.success(user, "User retrieved successfully"));
    }
}
