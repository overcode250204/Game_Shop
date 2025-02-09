package com.A4Team.GamesShop.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_STAFF("ROLE_STAFF");

    private final String value;

    UserRoleEnum(String value) {
        this.value = value;
    }

}
