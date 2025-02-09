package com.A4Team.GamesShop.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE(1),
    INACTIVE(0),
    BANNED(-1);

    private final int value;

    UserStatusEnum(int value) {
        this.value = value;
    }


    public static UserStatusEnum fromValue(int value) {
        for (UserStatusEnum status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid UserStatusEnum value: " + value);
    }

}
