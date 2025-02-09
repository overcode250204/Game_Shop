package com.A4Team.GamesShop.services.users;

import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAuthResponse processUserAvatar(UserAuthResponse user) {
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            String fileName = user.getAvatar().substring(user.getAvatar().lastIndexOf("/") + 1);
            user.setAvatar(serverUrl + "/api/files/avatar");
        }
        return user;
    }

}
