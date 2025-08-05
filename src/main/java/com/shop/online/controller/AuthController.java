package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.request.LoginRequest;
import com.shop.online.security.AccessToken;
import com.shop.online.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/auth")
@Tag(name = "auth-controller")
public class AuthController {

    private final AuthService authService;

    private static final String SIGN_IN_ENDPOINT = "/signin";

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "auth login")
    @PostMapping(SIGN_IN_ENDPOINT)
    public APIResponse<AccessToken> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        return APIResponse.okStatus(authService.signinAdmin(loginRequest));
    }

}
