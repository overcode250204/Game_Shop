package com.A4Team.GamesShop.services.users;

import com.A4Team.GamesShop.model.response.UserAuthResponse;

public interface UserService {
    UserAuthResponse processUserAvatar(UserAuthResponse user);
}
