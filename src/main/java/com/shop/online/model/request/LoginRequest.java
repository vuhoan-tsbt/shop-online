package com.shop.online.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class LoginRequest {
    @NotBlank(message = "Email can not null")
    @NotEmpty(message = "Password can not empty")
    @Email(message = "Email is wrong format")
    private String email;

    @NotBlank(message = "Password can not null")
    @NotEmpty(message = "Password can not empty")
    @Size(min = 8, message = "Password length must greater than 8")
    private String password;

}
