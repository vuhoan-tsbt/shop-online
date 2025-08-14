package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.request.LoginRequest;
import com.shop.online.security.AccessToken;
import com.shop.online.service.UserRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/auth")
@Tag(name = "user-auth-controller")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    private final String USER_REGISTER  = "/register";
    private static final String SIGN_IN_ENDPOINT = "/login";

    @PostMapping(USER_REGISTER)
    public APIResponse<?> userRegister(@Valid @RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        return APIResponse.okStatus(userRegisterService.userRegister(email, password));
    }

    @Operation(summary = "auth login")
    @PostMapping(SIGN_IN_ENDPOINT)
    public APIResponse<AccessToken> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        return APIResponse.okStatus(userRegisterService.userLogin(loginRequest));
    }
}
