package com.shop.online.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentException {
    public static final String ERROR_JWT_TOKEN_EXPIRED = "ERROR_JWT_TOKEN_EXPIRED";
    public static final String ERROR_JWT_INVALID_TOKEN = "ERROR_JWT_INVALID_TOKEN";

}
