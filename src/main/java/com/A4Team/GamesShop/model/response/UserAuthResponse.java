package com.A4Team.GamesShop.model.response;

import com.A4Team.GamesShop.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
    private int id;
    private String email;
    private String name;
    private String avatar;
    private UserRoleEnum role;
}
