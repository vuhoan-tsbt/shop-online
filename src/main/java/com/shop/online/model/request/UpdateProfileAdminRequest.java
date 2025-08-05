package com.shop.online.model.request;

import com.shop.online.utils.UserEnum;
import lombok.Data;

@Data
public class UpdateProfileAdminRequest {

    private String avatar;

    private String userName;

    private String phone;

    private String dateOfBirth;
}
