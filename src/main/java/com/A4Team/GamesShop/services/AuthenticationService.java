package com.A4Team.GamesShop.services;

import com.A4Team.GamesShop.repository.UserRepository;
import com.A4Team.GamesShop.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    public String login(@Valid LoginRequest request) {
        String token = "";
        return token;
    }
}
