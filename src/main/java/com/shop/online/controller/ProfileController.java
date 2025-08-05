package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.request.UpdateProfileAdminRequest;
import com.shop.online.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "ProfileController")
public class ProfileController {

    private final ProfileService profileService;
    private final String GET_PROFILE = "/get";

    private final String EDIT_INFO = "/edit";

    @Operation(summary = "get profile user")
    @GetMapping (GET_PROFILE)
    public APIResponse<?> getUser() {
        return APIResponse.okStatus(profileService.getAdmin());
    }

    @Operation(summary = "edit info")
    @PostMapping(EDIT_INFO)
    public APIResponse<?>  updateInfoAdmin(@RequestBody UpdateProfileAdminRequest profileAdminRequest){
        return APIResponse.okStatus(profileService.updateInfo(profileAdminRequest));
    }
}
