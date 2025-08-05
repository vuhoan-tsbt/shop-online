package com.shop.online.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.Date;

@Data
public class AccessToken {

    private String token;

    private Date expried;


    @NotNull(message = "Refresh token {validation.not-null}")
    @NotEmpty(message = "Refresh token {validation.not-empty}")
    private String refreshToken;

    private Integer userId;

    private String tokenType;


    private String roles;

    private String permission;
}
