package com.A4Team.GamesShop.dto;

import com.A4Team.GamesShop.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String name;
    private String avatar;
    private UserRoleEnum role;
}
