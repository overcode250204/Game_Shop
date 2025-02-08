package com.A4Team.GamesShop.services;

import com.A4Team.GamesShop.dto.UserDTO;
import com.A4Team.GamesShop.entities.User;
import com.A4Team.GamesShop.enums.UserRoleEnum;
import com.A4Team.GamesShop.enums.UserStatusEnum;
import com.A4Team.GamesShop.repository.UserRepository;
import com.A4Team.GamesShop.utils.GoogleAuthHelper;
import com.A4Team.GamesShop.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleAuthHelper googleAuthHelper;

    @Autowired
    private JwtHelper jwtHelper;

    public String authenWithGoogle(String authorizationCode) {
        Map<String, Object> userInfo = googleAuthHelper.getGoogleUserInfo(authorizationCode);

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String avatar = (String) userInfo.get("picture");

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user = null;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setName(name);
            user.setAvatar(avatar);
            user.setUpdatedAt(LocalDateTime.now());
        } else {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setAvatar(avatar);
            user.setRole(UserRoleEnum.ROLE_USER);
            user.setStatus(1);
            user.setCreatedAt(LocalDateTime.now());
        }

        userRepository.save(user);
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getAvatar(), user.getRole());
        return jwtHelper.generateToken(userDTO);

    }

    public String googleAuthUrl() {
        return googleAuthHelper.getGoogleAuthUrl();
    }









}
