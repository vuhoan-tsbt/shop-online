package com.shop.online.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public enum TokenEnum {
    TOKEN_JWT_EXPIRED(3, 7),
    TOKEN_REMEMBER_ME_EXPIRED(2, 30),
    REFRESH_TOKEN_EXPIRED(2, 60);
    private final int code;
    private final int value;

    TokenEnum(int code, int value) {
        this.code = code;
        this.value = value;

    }

}
