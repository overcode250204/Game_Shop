package com.A4Team.GamesShop.services;

import com.A4Team.GamesShop.dto.AuthDTO;
import com.A4Team.GamesShop.dto.UserDTO;
import com.A4Team.GamesShop.entities.User;
import com.A4Team.GamesShop.exception.LoginException;
import com.A4Team.GamesShop.repository.UserRepository;
import com.A4Team.GamesShop.request.LoginRequest;
import com.A4Team.GamesShop.response.BaseResponse;
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


    public BaseResponse<AuthDTO> login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new LoginException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginException("Invalid password");
        }

        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getAvatar(), user.getRole());
        String token = jwtHelper.generateToken(userDTO);
        AuthDTO authDTO = new AuthDTO(token);

        return BaseResponse.success(authDTO, "Login successfully");
    }
}
