package com.A4Team.GamesShop.services;

import com.A4Team.GamesShop.model.response.AuthResponse;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.entities.User;
import com.A4Team.GamesShop.exception.LoginException;
import com.A4Team.GamesShop.repository.UserRepository;
import com.A4Team.GamesShop.model.request.LoginRequest;
import com.A4Team.GamesShop.model.response.BaseResponse;
import com.A4Team.GamesShop.utils.JwtHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtHelper jwtHelper;


    public BaseResponse<AuthResponse> login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new LoginException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginException("Invalid password");
        }

        UserAuthResponse userDTO = new UserAuthResponse(user.getId(), user.getEmail(), user.getName(), user.getAvatar(), user.getRole());
        String token = jwtHelper.generateToken(userDTO);
        AuthResponse authDTO = new AuthResponse(token);

        return BaseResponse.success(authDTO, "Login successfully");
    }
}
